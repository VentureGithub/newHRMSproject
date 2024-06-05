package com.admin.controller;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.admin.model.request.SignUpRequest;
import com.admin.model.response.SignUpResponse;
import com.itextpdf.io.IOException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/super-admin")
public class CredentialController {

	@Autowired
	private RestTemplate restTemplate;

	@PostMapping("create-hr-credential")
	public ResponseEntity<InputStreamResource> cretedCredentialOfHr(@RequestBody SignUpRequest signUpData)
			throws IOException, MalformedURLException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<SignUpRequest> request = new HttpEntity<>(signUpData, headers);

		SignUpResponse data = this.restTemplate.postForObject(
				"http://localhost:7000/identity/access/admin/create-hr-credential", request, SignUpResponse.class);
//		Object dataa = data.getBody();
		System.out.print(data);
		return getcredentialPdf(data);
		// return data;
	}

	@GetMapping("/create")
	public ResponseEntity<InputStreamResource> getcredentialPdf(SignUpResponse data)
			throws IOException, MalformedURLException {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
		PdfDocument pdfDocument = new PdfDocument(pdfWriter);
		pdfDocument.setDefaultPageSize(PageSize.A4);

		Document document = new Document(pdfDocument);

		// putting the image
		String imagePath = "src/main/resources/ce_logo_circle_transparent.jpg";
		ImageData imageData = ImageDataFactory.create(imagePath);
		Image image = new Image(imageData);
		image.setFontSize(26);
		float x = pdfDocument.getDefaultPageSize().getWidth() / 2;
		float y = pdfDocument.getDefaultPageSize().getHeight() / 2;

		image.setFixedPosition(x - 50, y + 30);
		image.setOpacity(0.3f);

		document.add(image);

		// for decide table size
		float threecol = 190f;
		float twocol = 258f;
		float twocol150 = twocol + 150f;
		float twocolumWidth[] = { twocol150, twocol };
		float fullWidth[] = { threecol * 3 };

		Paragraph space = new Paragraph("\n");

		Table table = new Table(fullWidth);
		table.addCell(new Cell().add("Venture Consultancy Service").setFontSize(20f).setBorder(Border.NO_BORDER)
				.setBold().setTextAlignment(TextAlignment.RIGHT));
		Table nestedTable = new Table(new float[] { twocol / 2, twocol / 2 });
		nestedTable.addCell(new Cell().add("Date : " + data.getCreatedAt().toString().substring(0, 11))
				.setBorder(Border.NO_BORDER).setBold());

		table.addCell(new Cell().setBorder(Border.NO_BORDER));

		Border gb = new SolidBorder(Color.GRAY, 2f);
		document.add(table);
		document.add(space);
		document.add(nestedTable);
		document.add(space);

		Table nameandaddress = new Table(fullWidth);
		nameandaddress.addCell(new Cell().add("VCS").setBorder(Border.NO_BORDER));
		nameandaddress.addCell(new Cell().add("C3/74 palak tower Vibhuti khand \n gomti nagar Lucknow 226010 \n India")
				.setBorder(Border.NO_BORDER));
		document.add(nameandaddress);

		Table divider = new Table(fullWidth);
		divider.setBorder(gb);
		document.add(space);
		// document.add(divider);
		document.add(space);

		Table subject = new Table(fullWidth);
		subject.addCell(new Cell().add("Subject : Credential latter").setTextAlignment(TextAlignment.CENTER).setBold()
				.setBorder(Border.NO_BORDER).setUnderline());
		document.add(subject);

		document.add(space);

		Table twoColTable = new Table(fullWidth);
		twoColTable.addCell(new Cell().add("Dear " + data.getName()).setBorder(Border.NO_BORDER).setFontSize(15f));

		Table twoColTable2 = new Table(fullWidth);
		twoColTable.addCell(new Cell().add(
				"This is further with reference to the tests and interviews conducted by us. We are pleased to inform you, that we have decided to provide\r\n"
						+ "you appointment as Trainee at Nagarro.")
				.setBorder(Border.NO_BORDER));

		document.add(twoColTable);
		document.add(twoColTable2);

		document.add(space);

		Table credential = new Table(fullWidth);
		credential.addCell(new Cell().add("UserName :" + data.getEmail()).setBold().setBorder(Border.NO_BORDER));
		credential.addCell(new Cell().add("password :" + data.getPassword()).setBold().setBorder(Border.NO_BORDER));

		document.add(credential);
		document.add(space);

		Table dataforJoin = new Table(fullWidth);
		dataforJoin.addCell(new Cell().add("We welcome you to a pursuit of excellence with Nagarro.\r\n"
				+ "To help complete joining formalities, may we request you to carry the following documents with you on the date of joining:")
				.setBorder(Border.NO_BORDER));

		dataforJoin.addCell(new Cell().add("Thank You").setBorder(Border.NO_BORDER).setBold());

		document.add(dataforJoin);
		document.add(space);

		document.add(divider);

		Table termCondition = new Table(fullWidth);
		termCondition.addCell(new Cell().add("Term And Condition").setBorder(Border.NO_BORDER).setBold());
		termCondition.addCell(
				new Cell().add("1. toy are good ok bhai jsidcs bwcbs bilbhcbs  sbcishbc").setBorder(Border.NO_BORDER));

		termCondition.addCell(new Cell().add(
				"2. toy are good ok bhai jsidcs bwcbs bilbhcbs  sbcishbc hvjsd bhbs bkhbhca hbhasb cbhsbc sbchbs hi all all si good and ")
				.setBorder(Border.NO_BORDER));
		document.add(space);
		document.add(termCondition.setMarginBottom(10f));

		document.close();

		byte[] content = byteArrayOutputStream.toByteArray();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("filename", "invoice.pdf");

		ByteArrayInputStream stream = new ByteArrayInputStream(content);

		return new ResponseEntity<>(new InputStreamResource(stream), headers, HttpStatus.OK);
	}

	static Cell getBillingandShhippingCell(String textValue) {
		return new Cell().add(textValue).setFontSize(12f).setBold().setBorder(Border.NO_BORDER)
				.setTextAlignment(TextAlignment.LEFT);
	}

	static Cell getCell10Left(String textValue, boolean isBolean) {
		Cell myCell = new Cell().add(textValue).setFontSize(10f).setBorder(Border.NO_BORDER)
				.setTextAlignment(TextAlignment.LEFT);
		return isBolean ? myCell.setBold() : myCell;

	}

	@GetMapping("/ss")
	public String ss() {
		return "sss";
	}

}
