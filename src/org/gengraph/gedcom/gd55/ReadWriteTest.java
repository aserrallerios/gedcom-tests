package org.gengraph.gedcom.gd55;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;

import org.folg.gedcom.model.EventFact;
import org.folg.gedcom.model.Gedcom;
import org.folg.gedcom.model.GeneratorCorporation;
import org.folg.gedcom.model.Person;
import org.folg.gedcom.model.Repository;
import org.folg.gedcom.model.Submitter;
import org.folg.gedcom.parser.ModelParser;
import org.folg.gedcom.visitors.GedcomWriter;
import org.junit.Test;
import org.testng.Assert;
import org.xml.sax.SAXParseException;

public class ReadWriteTest {

	ModelParser modelParser = new ModelParser();
	GedcomWriter writer = new GedcomWriter();

	@Test
	public void readGd55File() throws URISyntaxException, SAXParseException, IOException {
		final URL gedcomUrl = this.getClass().getClassLoader()
				.getResource("resources/gd55/Case001-AddressStructure.ged");
		final File gedcomFile = new File(gedcomUrl.toURI());
		this.modelParser = new ModelParser();

		final Gedcom gedcom = this.modelParser.parseGedcom(gedcomFile);
		gedcom.createIndexes();
		Assert.assertNotNull(gedcom);

		Assert.assertNotNull(gedcom.getHeader());
		Assert.assertNotNull(gedcom.getHeader().getGenerator());
		final GeneratorCorporation generatorCorporation = gedcom.getHeader().getGenerator()
				.getGeneratorCorporation();
		Assert.assertNotNull(generatorCorporation);
		Assert.assertEquals(generatorCorporation.getAddress().getValue(), "5000 MyCorpCampus Dr\n"
				+ "Hometown, ZZ  99999\n" + "United States");
		Assert.assertEquals(generatorCorporation.getAddress().getAddressLine1(), "__ADR1_VALUE__");
		Assert.assertEquals(generatorCorporation.getAddress().getAddressLine2(), "__ADR2_VALUE__");
		Assert.assertEquals(generatorCorporation.getAddress().getAddressLine3(), "5000 MyCorpCampus Dr");
		Assert.assertEquals(generatorCorporation.getAddress().getCity(), "Hometown");
		Assert.assertEquals(generatorCorporation.getAddress().getState(), "ZZ");
		Assert.assertEquals(generatorCorporation.getAddress().getPostalCode(), "99999");
		Assert.assertEquals(generatorCorporation.getAddress().getCountry(), "United States");
		Assert.assertEquals(generatorCorporation.getPhone(), "866-000-0000");
		Assert.assertEquals(generatorCorporation.getEmail(), "info@mycorporation.com");
		Assert.assertEquals(generatorCorporation.getFax(), "866-111-1111");
		Assert.assertEquals(generatorCorporation.getWww(), "http://www.mycorporation.org/");

		final Submitter submitter = gedcom.getSubmitter();
		Assert.assertNotNull(submitter);
		Assert.assertEquals(submitter.getAddress().getValue(), "5000 MyCorpCampus Dr\n"
				+ "Hometown, ZZ  99999\n" + "United States");
		Assert.assertEquals(submitter.getAddress().getAddressLine1(), "__ADR1_VALUE__");
		Assert.assertEquals(submitter.getAddress().getAddressLine2(), "__ADR2_VALUE__");
		Assert.assertEquals(submitter.getAddress().getAddressLine3(), "5000 MyCorpCampus Dr");
		Assert.assertEquals(submitter.getAddress().getCity(), "Hometown");
		Assert.assertEquals(submitter.getAddress().getState(), "ZZ");
		Assert.assertEquals(submitter.getAddress().getPostalCode(), "99999");
		Assert.assertEquals(submitter.getAddress().getCountry(), "United States");
		Assert.assertEquals(submitter.getPhone(), "866-000-0000");
		Assert.assertEquals(submitter.getEmail(), "info@mycorporation.com");
		Assert.assertEquals(submitter.getFax(), "866-111-1111");
		Assert.assertEquals(submitter.getWww(), "http://www.mycorporation.org/");

		Assert.assertNotNull(gedcom.getPeople());
		Assert.assertEquals(gedcom.getPeople().size(), 1);
		final Person person = gedcom.getPeople().get(0);
		Assert.assertNotNull(person);
		Assert.assertEquals(person.getAddress().getValue(), "5000 MyCorpCampus Dr\n"
				+ "Hometown, ZZ  99999\n" + "United States");
		Assert.assertEquals(person.getAddress().getAddressLine1(), "__ADR1_VALUE__");
		Assert.assertEquals(person.getAddress().getAddressLine2(), "__ADR2_VALUE__");
		Assert.assertEquals(person.getAddress().getAddressLine3(), "5000 MyCorpCampus Dr");
		Assert.assertEquals(person.getAddress().getCity(), "Hometown");
		Assert.assertEquals(person.getAddress().getState(), "ZZ");
		Assert.assertEquals(person.getAddress().getPostalCode(), "99999");
		Assert.assertEquals(person.getAddress().getCountry(), "United States");
		Assert.assertEquals(person.getPhone(), "866-000-0000");
		Assert.assertEquals(person.getEmail(), "info@mycorporation.com");
		Assert.assertEquals(person.getFax(), "866-111-1111");
		Assert.assertEquals(person.getWww(), "http://www.mycorporation.org/");

		Assert.assertNotNull(person.getEventsFacts());
		Assert.assertEquals(person.getEventsFacts().size(), 1);
		final EventFact eventFact = person.getEventsFacts().get(0);
		Assert.assertEquals(eventFact.getAddress().getValue(), "Arlington National Cemetery\n"
				+ "State Hwy 110 & Memorial Dr\n" + "Arlington, VA  22211\n" + "United States");
		Assert.assertEquals(eventFact.getAddress().getAddressLine1(), "__ADR1_VALUE__");
		Assert.assertEquals(eventFact.getAddress().getAddressLine2(), "__ADR2_VALUE__");
		Assert.assertEquals(eventFact.getAddress().getAddressLine3(), "__ADR3_VALUE__");
		Assert.assertEquals(eventFact.getAddress().getCity(), "Arlington");
		Assert.assertEquals(eventFact.getAddress().getState(), "VA");
		Assert.assertEquals(eventFact.getAddress().getPostalCode(), "22211");
		Assert.assertEquals(eventFact.getAddress().getCountry(), "United States");
		Assert.assertEquals(eventFact.getPhone(), "877-907-8585");
		Assert.assertEquals(eventFact.getEmail(), "info@arlingtoncemetery.mil");
		Assert.assertEquals(eventFact.getFax(), "877-111-1111");
		Assert.assertEquals(eventFact.getWww(), "http://www.arlingtoncemetery.mil/");

		Assert.assertNotNull(gedcom.getRepositories());
		Assert.assertEquals(gedcom.getRepositories().size(), 1);
		final Repository repository = gedcom.getRepositories().get(0);
		Assert.assertEquals(repository.getAddress().getValue(), "5000 MyCorpCampus Dr\n"
				+ "Hometown, ZZ  99999\n" + "United States");
		Assert.assertEquals(repository.getAddress().getAddressLine1(), "__ADR1_VALUE__");
		Assert.assertEquals(repository.getAddress().getAddressLine2(), "__ADR2_VALUE__");
		Assert.assertEquals(repository.getAddress().getAddressLine3(), "5000 MyCorpCampus Dr");
		Assert.assertEquals(repository.getAddress().getCity(), "Hometown");
		Assert.assertEquals(repository.getAddress().getState(), "ZZ");
		Assert.assertEquals(repository.getAddress().getPostalCode(), "99999");
		Assert.assertEquals(repository.getAddress().getCountry(), "United States");
		Assert.assertEquals(repository.getPhone(), "866-000-0000");
		Assert.assertEquals(repository.getEmail(), "info@mycorporation.com");
		Assert.assertEquals(repository.getFax(), "866-111-1111");
		Assert.assertEquals(repository.getWww(), "https://www.mycorporation.com/");
	}

	@Test
	public void readGd55StressFile() throws URISyntaxException, SAXParseException, IOException {
		final URL gedcomUrl = this.getClass().getClassLoader().getResource("resources/gd55/TGC551.ged");
		final File gedcomFile = new File(gedcomUrl.toURI());
		this.modelParser = new ModelParser();

		final Gedcom gedcom = this.modelParser.parseGedcom(gedcomFile);
		gedcom.createIndexes();
		Assert.assertNotNull(gedcom);
	}

	@Test
	public void readWriteGd55StressFile() throws URISyntaxException, SAXParseException, IOException {
		final URL gedcomUrl = this.getClass().getClassLoader().getResource("resources/gd55/TGC551.ged");
		final File gedcomFile = new File(gedcomUrl.toURI());
		this.modelParser = new ModelParser();

		final Gedcom gedcom = this.modelParser.parseGedcom(gedcomFile);
		gedcom.createIndexes();
		Assert.assertNotNull(gedcom);

		final File gedcomOutFile = new File("./src/resources/out/" + gedcomFile.getName());
		if (!gedcomOutFile.exists()) {
			gedcomOutFile.createNewFile();
		}
		final OutputStream out = (new FileOutputStream(gedcomOutFile));
		this.writer.write(gedcom, out);
		out.close();
	}
}
