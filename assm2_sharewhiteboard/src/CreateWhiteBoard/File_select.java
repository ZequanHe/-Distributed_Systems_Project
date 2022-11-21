package CreateWhiteBoard;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class File_select extends FileFilter{
	String suffix;
	String descri;
	
	public File_select(String suffix,String descri) {
		this.suffix = suffix;
		this.descri = descri;
	}

	@Override
	public boolean accept(File f) {
		// TODO Auto-generated method stub
		if(f.isDirectory()) {
			return true;
		}
		String name = f.getName().toUpperCase();
		if (name.endsWith(this.suffix.toUpperCase())) {
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return this.descri;
	}
	
	public String getSuffix() {
		return this.suffix;
	}

}
