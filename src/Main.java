import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Aakash on 5/19/2017.
 */
public class Main {
    static File source = new File("D:\\Code");
    static File destination = new File("C:\\Test\\Destination");
    public static void main(String[] args) throws IOException {

        findDiscrepancies(source, destination);


    }
    static void findDiscrepancies(File source, File destination) throws IOException {

        Files.walkFileTree(source.toPath(), new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                File sor = new File(file.toString());
                File dest = new File(destination.toString()+file.toString().substring(source.toString().length(), file.toString().length()));

                if (sor.isFile()) {

                    if (!dest.exists()) {
                        File temp = new File(dest.toString().substring(0, dest.toString().length() - dest.getName().length()-1));

                        temp.mkdirs();
                        System.out.println("copying " + sor.toString() + " to " + dest.toString());
                        Files.copy(sor.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);


                    }
                    else if (sor.lastModified() > dest.lastModified()) {
                        System.out.println("copying " + sor.toString() + " to " + dest.toString());
                        Files.copy(sor.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    }
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });

    }
}
