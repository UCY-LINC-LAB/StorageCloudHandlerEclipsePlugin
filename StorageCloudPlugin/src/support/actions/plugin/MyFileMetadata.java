package support.actions.plugin;

public class MyFileMetadata {

	private boolean active ;
	
	private String localPath;
	
	private String DropboxPath;
	
	MyFileMetadata(boolean flag, String srt1,String srt2){
		active =flag;
		localPath =srt1;
		DropboxPath= srt2;
	}
	
	
	public void setActive(boolean flag){
		active=flag;
	}
	
	public void setLocalPath(String srt){
		localPath=srt;
	}
	public void setDropboxPath(String srt){
		DropboxPath=srt;
	}
	
	public boolean getActive(){
		return active;
	}
	
	public String getlocalPath(){
		return localPath;
	}
	
	public String getDropboxPath(){
		return DropboxPath;
	}
	
	public String toString(){
		String rtrn ="Active :"+active+" LocalPath :" +localPath+" Dropbox :" +DropboxPath;
		return rtrn;
		
	}
}
