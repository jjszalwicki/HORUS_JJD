package org.example;

import java.util.*;

public class FileCabinet implements Cabinet {

    private List<Folder> folders;

    @Override
    public Optional<Folder> findFolderByName(String name) {
        for (Folder folder : folders) {
            Optional<Folder> result = findFolderByNameMulti(folder, name);
            if (result.isPresent()) {
                return result;
            }
        }
        return Optional.empty();
    }

    private Optional<Folder> findFolderByNameMulti(Folder folder, String name) {
        if (folder.getName().equals(name)) {
            return Optional.of(folder);
        }

        if (folder instanceof MultiFolder multiFolder) {
            for (Folder subFolder : multiFolder.getFolders()) {
                Optional<Folder> result = findFolderByNameMulti(subFolder, name);
                if (result.isPresent()) {
                    return result;
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Folder> findFolderBySize(String size) {
        List<Folder> result = new ArrayList<>();
        for (Folder folder : folders) {
            findFolderBySizeMulti(folder, size, result);
        }
        return result;
    }

    private void findFolderBySizeMulti(Folder folder, String size, List<Folder> result) {
        if (folder.getSize().equals(size)) {
            result.add(folder);
        }

        if (folder instanceof MultiFolder multiFolder) {
            for (Folder subFolder : multiFolder.getFolders()) {
                findFolderBySizeMulti(subFolder, size, result);
            }
        }
    }

    @Override
    public int count() {
        int count = 0;
        for (Folder folder : folders) {
            count += countMulti(folder);
        }
        return count;
    }

    private int countMulti(Folder folder) {
        int count = 1;

        if (folder instanceof MultiFolder multiFolder) {
            for (Folder subFolder : multiFolder.getFolders()) {
                count += countMulti(subFolder);

            }
        }

        return count;
    }
}