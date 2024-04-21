package restaurant.menu;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class MenuApplication  implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(MenuApplication.class, args);


		try {
			File pdfFile = new File("java.pdf");
			PDDocument pDDocument = PDDocument.load(pdfFile);
			PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm();
			PDField field = pDAcroForm.getField("txt_1");
			field.setValue("This is a first field printed by Java");
			field = pDAcroForm.getField("txt_2");
			field.setValue("This is a second field printed by Java");
			pDDocument.save("C:\\Users\\Administrator\\Desktop\\MATTEO\\output.pdf");
			pDDocument.close();
		} catch (IOException e) {
			e.printStackTrace();
		}




	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/swagger-ui/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/springdoc-openapi-ui/")
				.resourceChain(false);
	}
}
