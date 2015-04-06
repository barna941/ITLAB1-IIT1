package jpa;

public class CommonData {

	private static final String PERSISTENCE_UNIT_NAME = "Amigo_DB";
	private static final String DBASE_DIRECTORY = "local_db";

	public static String getDir() {
		return DBASE_DIRECTORY;
	}
	public static String getUnit() {
		return PERSISTENCE_UNIT_NAME;
	}

}
