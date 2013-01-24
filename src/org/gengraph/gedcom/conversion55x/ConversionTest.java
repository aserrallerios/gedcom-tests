package org.gengraph.gedcom.conversion55x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.folg.gedcom.model.Gedcom;
import org.folg.gedcom.parser.ModelParser;
import org.folg.gedcom.visitors.GedcomWriter;
import org.gedcomx.conclusion.Person;
import org.gedcomx.conclusion.Relationship;
import org.gedcomx.contributor.Agent;
import org.gedcomx.conversion.TestConversionResult;
import org.gedcomx.conversion.gedcom.dq55.GedcomMapper;
import org.gedcomx.fileformat.DefaultXMLSerialization;
import org.gedcomx.fileformat.GedcomxOutputStream;
import org.gedcomx.rt.GedcomxConstants;
import org.gedcomx.source.SourceDescription;
import org.gedcomx.tools.Gedcom2Gedcomx;
import org.junit.Test;
import org.testng.Assert;
import org.xml.sax.SAXParseException;

public class ConversionTest {

	ModelParser modelParser = new ModelParser();
	GedcomWriter writer = new GedcomWriter();

	@Test
	public void gedxConversion() throws Exception {
		final String[] args = new String[] { "-i",
				"C:/Users/Albert/workspace/gedcom-tests/src/resources/gd55/TGC551.ged", "-o",
				"C:/Users/Albert/workspace/gedcom-tests/src/resources/out/ged55stresstox.jar", "-vv" };
		Gedcom2Gedcomx.main(args);
	}

	@Test
	public void read55WriteX() throws SAXParseException, IOException, URISyntaxException {
		final URL gedcomUrl = this.getClass().getClassLoader().getResource("resources/gd55/TGC551.ged");
		final File gedcomFile = new File(gedcomUrl.toURI());
		this.modelParser = new ModelParser();

		final Gedcom gedcom = this.modelParser.parseGedcom(gedcomFile);
		gedcom.createIndexes();
		Assert.assertNotNull(gedcom);

		final GedcomMapper mapper = new GedcomMapper();

		final TestConversionResult result = new TestConversionResult();
		mapper.toGedcomx(gedcom, result);
		Assert.assertNotNull(result);

		final File gedcomxOutFile = new File("./src/resources/out/ged55stresstox.jar");
		final DefaultXMLSerialization ser = new DefaultXMLSerialization();
		ser.setKnownContentTypes(new HashSet<String>());
		final GedcomxOutputStream gedxOutputStream = new GedcomxOutputStream(new FileOutputStream(
				gedcomxOutFile), ser);

		// for (final Entry<String, String> attribute :
		// result.getEntryAttributes()) {
		// gedxOutputStream.addAttribute(attribute.getKey(),
		// attribute.getValue());
		// }

		final List<Object> resources = new ArrayList<>();
		resources.addAll(result.getPersons());
		resources.addAll(result.getRelationships());
		resources.addAll(result.getContributors());
		resources.addAll(result.getOrganizations());
		resources.addAll(result.getSourceDescriptions());

		for (final Object resource : resources) {
			if (resource instanceof Person) {
				final Person person = (Person) resource;
				gedxOutputStream.addResource(GedcomxConstants.GEDCOMX_XML_MEDIA_TYPE,
						"persons/" + person.getId(), person, new Date(),
						result.getEntryAttributes(person.getId()));
			} else if (resource instanceof Relationship) {
				final Relationship relationship = (Relationship) resource;
				gedxOutputStream.addResource(GedcomxConstants.GEDCOMX_XML_MEDIA_TYPE, "relationships/"
						+ relationship.getId(), relationship, null,
						result.getEntryAttributes(relationship.getId()));
			} else if (resource instanceof Agent) {
				final Agent person = (Agent) resource;
				gedxOutputStream.addResource(GedcomxConstants.GEDCOMX_XML_MEDIA_TYPE, "contributors/"
						+ person.getId(), person, null, result.getEntryAttributes(person.getId()));
			} else if (resource instanceof SourceDescription) {
				final SourceDescription description = (SourceDescription) resource;
				gedxOutputStream.addResource(GedcomxConstants.GEDCOMX_XML_MEDIA_TYPE, "descriptions/"
						+ description.getId(), description, null,
						result.getEntryAttributes(description.getId()));
			} else {
				// TODO: MORE RESOURCE TYPES?
			}
		}
		gedxOutputStream.close();
	}

}
