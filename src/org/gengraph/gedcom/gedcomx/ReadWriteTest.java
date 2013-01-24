package org.gengraph.gedcom.gedcomx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.JarFile;

import org.gedcomx.conclusion.Person;
import org.gedcomx.conclusion.Relationship;
import org.gedcomx.contributor.Agent;
import org.gedcomx.fileformat.DefaultXMLSerialization;
import org.gedcomx.fileformat.GedcomxFile;
import org.gedcomx.fileformat.GedcomxFileEntry;
import org.gedcomx.fileformat.GedcomxOutputStream;
import org.gedcomx.rt.GedcomxConstants;
import org.gedcomx.source.SourceDescription;
import org.gengraph.gedcom.utils.Logging;
import org.junit.Test;

public class ReadWriteTest {

	@Test
	public void readGedcomxFile() throws IOException, URISyntaxException {
		final URL gedcomxUrl = this.getClass().getClassLoader().getResource("resources/gedcomx/FsTest.gedx");
		final File gedcomxFile = new File(gedcomxUrl.toURI());
		final JarFile jarFile = new JarFile(gedcomxFile);
		final GedcomxFile gedxFile = new GedcomxFile(jarFile);

		Logging.logger.info(gedxFile.getAttributes().toString());
		final Iterable<GedcomxFileEntry> entries = gedxFile.getEntries();
		for (final GedcomxFileEntry entry : entries) {
			final String name = entry.getJarEntry().getName();
			if ((name != null) && (!"META-INF/MANIFEST.MF".equals(name))) {
				Logging.logger.info(entry.toString());
				gedxFile.readResource(entry);
			}
		}
		gedxFile.close();
		jarFile.close();
	}

	@Test
	public void readWriteGedcomxFile() throws IOException, URISyntaxException {
		final URL gedcomxUrl = this.getClass().getClassLoader().getResource("resources/gedcomx/FsTest.gedx");
		final File gedcomxFile = new File(gedcomxUrl.toURI());
		final JarFile jarFile = new JarFile(gedcomxFile);
		final GedcomxFile gedxFile = new GedcomxFile(jarFile);

		final Map<String, String> attributes = gedxFile.getAttributes();
		Logging.logger.info(attributes.toString());
		final Iterable<GedcomxFileEntry> entries = gedxFile.getEntries();
		final List<Object[]> resources = new ArrayList<>();
		for (final GedcomxFileEntry entry : entries) {
			final String name = entry.getJarEntry().getName();
			if ((name != null) && (!"META-INF/MANIFEST.MF".equals(name))) {
				Logging.logger.info(entry.toString());
				gedxFile.readResource(entry);
				resources.add(new Object[] { gedxFile.readResource(entry), entry.getAttributes() });
			}
		}
		gedxFile.close();
		jarFile.close();

		final File gedcomxOutFile = new File("./src/resources/out/" + gedcomxFile.getName());
		final DefaultXMLSerialization ser = new DefaultXMLSerialization();
		ser.setKnownContentTypes(new HashSet<String>());
		final GedcomxOutputStream gedxOutputStream = new GedcomxOutputStream(new FileOutputStream(
				gedcomxOutFile), ser);

		for (final Entry<String, String> attribute : attributes.entrySet()) {
			gedxOutputStream.addAttribute(attribute.getKey(), attribute.getValue());
		}
		for (final Object[] resource : resources) {
			if (resource[0] instanceof Person) {
				final Person person = (Person) resource[0];
				gedxOutputStream.addResource(GedcomxConstants.GEDCOMX_XML_MEDIA_TYPE,
						"persons/" + person.getId(), person, new Date(), (Map<String, String>) resource[1]);
			} else if (resource[0] instanceof Relationship) {
				final Relationship relationship = (Relationship) resource[0];
				gedxOutputStream.addResource(GedcomxConstants.GEDCOMX_XML_MEDIA_TYPE, "\\relationships\\"
						+ relationship.getId(), relationship, null, (Map<String, String>) resource[1]);
			} else if (resource[0] instanceof Agent) {
				final Agent person = (Agent) resource[0];
				gedxOutputStream.addResource(GedcomxConstants.GEDCOMX_XML_MEDIA_TYPE, "contributors/"
						+ person.getId(), person, null, (Map<String, String>) resource[1]);
			} else if (resource[0] instanceof SourceDescription) {
				final SourceDescription description = (SourceDescription) resource[0];
				gedxOutputStream.addResource(GedcomxConstants.GEDCOMX_XML_MEDIA_TYPE, "descriptions/"
						+ description.getId(), description, null, (Map<String, String>) resource[1]);
			} else {
				// TODO: MORE RESOURCE TYPES?
			}
		}
		gedxOutputStream.close();
	}
}
