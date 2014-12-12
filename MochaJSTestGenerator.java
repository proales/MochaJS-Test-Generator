import java.io.File;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MochaJSTestGenerator {
	public static void main(String [] args)
	{
		String repo = "app-name";
		String repoShort = "name";
		String repoPretty = "Name";
		
		File x = new File(repo + "/js/src");
		Collection y = listFileTree(x);
		File[] fileArray = (File[]) y.toArray(new File[y.size()]);
		for(int i = 0; i<fileArray.length ; i++){
			File file = fileArray[i];
			String tempName = file.getPath().replaceFirst("/src/", "/tests/specs/");
			String fileName = tempName.substring(0, tempName.length() - 3) + ".spec.js";
			String uniqueName = fileName.replace(repo + "/js/tests/specs/", "").replace(".spec.js", "");
			String prettyName = TitleCase(uniqueName.replace("/", " - "));
			String dirName = fileName.substring(0, fileName.lastIndexOf("/"));
			boolean isJS = tempName.contains(".js");
			if (isJS){
				try {
					
					File newDir = new File(dirName);
					newDir.mkdirs();
				} catch (Exception e){}
			}
			if (isJS){
				try {
					System.out.println(dirName);
					PrintWriter writer;
					writer = new PrintWriter(fileName, "UTF-8");
					writer.println("define([");
					writer.println("    '"+ repoShort +"/" + uniqueName + "',");
					writer.println("], function (TestFile) {");
					writer.println("");
					writer.println("    describe('"+ repoPretty +" - " + prettyName + " - Tests', function() {");
					writer.println("");
					writer.println("        it('Sample Test', function (){");
					writer.println("            expect(true).to.be.true;");
					writer.println("        });");
					writer.println("");
					writer.println("    });");
					writer.println("");
					writer.println("});");
					
					writer.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String TitleCase(String source){
		StringBuffer res = new StringBuffer();

	    String[] strArr = source.split(" ");
	    for (String str : strArr) {
	        char[] stringArray = str.trim().toCharArray();
	        stringArray[0] = Character.toUpperCase(stringArray[0]);
	        str = new String(stringArray);

	        res.append(str).append(" ");
	    }

		return res.toString().trim();
	}
	
	public static Collection<File> listFileTree(File dir) {
	    Set<File> fileTree = new HashSet<File>();
	    for (File entry : dir.listFiles()) {
	        if (entry.isFile()) fileTree.add(entry);
	        else fileTree.addAll(listFileTree(entry));
	    }
	    return fileTree;
	}
}
