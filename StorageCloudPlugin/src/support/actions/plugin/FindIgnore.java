package support.actions.plugin;


public class FindIgnore {

	private static  char PathSeperetor='/';
	
	public static String IgnorePath(String Path, String project){
		
		String oper_syste= System.getProperty("os.name");
		
	 	if (oper_syste.contains("Windows")){
	 		PathSeperetor='\\';
	 	}
	 
		String pathignore=Path;
		
		int length = pathignore.indexOf(project);	
		pathignore=pathignore.substring(0, length-1);
		return pathignore+PathSeperetor+project+PathSeperetor+".ignore";
		
		
	}
	

}
