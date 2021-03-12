package Zadanie1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class Visitor extends SimpleFileVisitor<Path> {
    Path directoryPath;
    Path resultPath;
    FileChannel outputChannel;
    Charset inCharset = Charset.forName("Cp1250");
    Charset outCharset = Charset.forName("UTF-8");

    public Visitor(Path directoryPath, Path resultPath, FileChannel outputChannel) {
        this.directoryPath = directoryPath;
        this.resultPath = resultPath;
        this.outputChannel = outputChannel;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (attrs.isRegularFile()){

            FileChannel fileChannel = FileChannel.open(file);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect((int) fileChannel.size());
            fileChannel.read(byteBuffer);
            byteBuffer.flip();
            CharBuffer charBuffer = inCharset.decode(byteBuffer);
            ByteBuffer encoded = outCharset.encode(charBuffer);
            outputChannel.write(encoded);

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
}
