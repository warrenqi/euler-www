package euler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.rocksdb.RocksDB;

import com.google.common.io.Resources;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class App
{

    static
    {
        RocksDB.loadLibrary();
    }

    public static void main(String[] args)
    {
        Config config = ConfigFactory.load();
        if (args != null && args.length == 1) {
            System.out.println("using 1st arg as path to config");
            File configFile = new File(args[0]);
            config = ConfigFactory.parseFile(configFile);
        }
        System.out.println("loaded config:\n" + config.entrySet());
        
        URL resource = Resources.getResource("placeholder.txt");
        File placeholder = new File(resource.getFile());
        System.out.println("placeholder.txt = " + resource.getPath());
        Path project_root = placeholder.toPath().getParent().getParent().getParent();
        System.out.println("project root = " + project_root.normalize().toString());
        try
        {
            FileStore fs = Files.getFileStore(project_root);
            long freespace = fs.getUsableSpace();
            System.out.println("freespace GB = " + freespace / FileUtils.ONE_GB);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        String db_path = FilenameUtils.concat(project_root.toString(), "rocksdb");

    }

}
