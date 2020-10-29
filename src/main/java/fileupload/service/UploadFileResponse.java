package fileupload.service;

public class UploadFileResponse {

    private String filename;
    private String fileDownLoaduri;
    private String fileType;
    private long size;

    public UploadFileResponse(String filename, String fileDownLoaduri, String fileType, long size) {
        this.filename = filename;
        this.fileDownLoaduri = fileDownLoaduri;
        this.fileType = fileType;
        this.size = size;
    }


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileDownLoaduri() {
        return fileDownLoaduri;
    }

    public void setFileDownLoaduri(String fileDownLoaduri) {
        this.fileDownLoaduri = fileDownLoaduri;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
