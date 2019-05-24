# oneplusburstorganizer-java

This is a tool for organizing images captured in burst mode in a OnePlus smartphone. The tool organizes related images into a directory using the file names. A file name (burst image) is of the format IMG_<date-time>_<burst-id>_<sequence-id> while other non-burst image files do not have the burst-id or sequence-id. The tool creates separate directories with diretory names based on the burst-id and moves all image files with similar burst-id into the relavant directory.

The location of the images are specified through the source direcotry and the organized images will be found in the destination directory.
