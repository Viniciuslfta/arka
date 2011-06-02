/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.menus;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author sPeC
 */
public class SaveFilesFilter extends FileFilter {

    public SaveFilesFilter() {
    }

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    public static String addExtension(File f) {
        String path = f.getAbsolutePath();

        if (!"ark".equals(getExtension(f))) {
            return path.concat(".ark");
        }
        return path;
    }

    @Override
    public boolean accept(File file) {

        if (file.isDirectory()) {
            return true;
        }

        return "ark".equals(getExtension(file));
    }

    @Override
    public String getDescription() {
        return "Jogo de Arkanoid";
    }
}
