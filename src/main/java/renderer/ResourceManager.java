package renderer;

import java.io.File;

public class ResourceManager {

    private String rootDir;

    public ResourceManager(String rootDir){
        rootDir = rootDir.replace('\\', '/');
        if(rootDir.charAt(rootDir.length() - 1) != '/')
            rootDir += '/';
        this.rootDir = rootDir;
    }

    public String getAbsoluteFilePath(String relativePath){
        return rootDir + relativePath;
    }

    public File getFile(String relativePath){
        return new File(rootDir + relativePath);
    }

    public File getResource(String name){
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(name).getFile());
    }

    public String getRootDirFileUrl(){
        return "file:///" + rootDir;
    }

}
