package org.gengraph.gedcom.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Logging {

	public static Logger logger = Logger.getLogger("INFO_LOGGER");
	static {
		Logging.logger.setLevel(Level.INFO);
	}
}
