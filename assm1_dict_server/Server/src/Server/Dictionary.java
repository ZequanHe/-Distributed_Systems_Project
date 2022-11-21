/*He Zequan 1068069*/
package Server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Dictionary {
	private HashMap<String,String> dict;
	private String dic_path = "Dictionary.dat";
	
	public Dictionary(String path) {
		dic_path = path;
		try {
			ObjectInputStream file = new ObjectInputStream(new FileInputStream(dic_path));
			dict = (HashMap<String, String>) file.readObject();
			file.close();
		} catch (FileNotFoundException e ) {
			System.out.println("Cannot find file. setup a dictionary ");
			setupDict();
		} catch (ClassNotFoundException e) {
			System.out.println("Wrong file format! setup a dictionary.");
			setupDict();
		}
		catch (Exception e) {
			System.out.println("error :," + e.getMessage());
			e.printStackTrace();
		}
	}
	public String getDic_path() {
		return dic_path;
	}
	/**
	 * create a new dictionary
	 */
	private void createDict(String path) {
		dict = new HashMap<String,String>();
		try {
			ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(path));
			file.writeObject(dict);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setupDict() {
		dic_path = "Dictionary.dat";
		try {
			ObjectInputStream file = new ObjectInputStream(new FileInputStream(dic_path));
			dict = (HashMap<String, String>) file.readObject();
			file.close();
		}catch (FileNotFoundException e ) {
			System.out.println("create new one");
			createDict(dic_path);
		}catch (Exception e) {
			System.out.println("error :," + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public Dictionary() {
		dict = new HashMap<String,String>();
		dic_path = "";
	}
	
	public String getdic_path() {
		return dic_path;
	}
	public synchronized String search(String word) {
		return dict.get(word);
	}
	public synchronized Boolean word_exist(String word) {
		return dict.containsKey(word);
	}
	
	public synchronized Boolean add(String word, String meaning) {
		if(dict.containsKey(word)) {
			return false;
		}else {
			dict.put(word, meaning);
			try {
				ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(dic_path));
				file.writeObject(dict);
				file.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
	}
	
	public synchronized Boolean delete(String word) {
		if(dict.containsKey(word)) {
			dict.remove(word);
			try {
				ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(dic_path));
				file.writeObject(dict);
				file.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}
	public synchronized Boolean update(String word, String meaning) {
		if(dict.containsKey(word)) {
			dict.put(word,meaning);
			try {
				ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(dic_path));
				file.writeObject(dict);
				file.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

}
