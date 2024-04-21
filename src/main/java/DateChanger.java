import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DateChanger extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        // Get the current open document
        VirtualFile file = event.getData(CommonDataKeys.VIRTUAL_FILE);
        if (file != null) {
            String filePath = file.getPath();
            File documentFile = new File(filePath);

            // Path to the descriptor.json file
            String dirPath = documentFile.getParent();
            String descriptorPath = Paths.get(dirPath, DateChangerConstants.FILE_NAME).toString();

            try {
                if (Files.exists(Paths.get(descriptorPath))) {
                    // Read the contents of descriptor.json
                    String rawData = Files.readString(Paths.get(descriptorPath));

                    // Replace the date in the format Date(xxx) with a new date
                    String updatedData = rawData.replaceAll(DateChangerConstants.REGEX_DATE, DateChangerUtils.getUtcDate());

                    // Write the updated data back to the file
                    Files.writeString(Paths.get(descriptorPath), updatedData);

                    Messages.showMessageDialog(DateChangerConstants.SUCCESS_MESSAGE, DateChangerConstants.SUCCESS_TITLE, Messages.getInformationIcon());
                }
            } catch (IOException exception) {
                Messages.showMessageDialog(DateChangerConstants.ERROR_MESSAGE + exception.getMessage(), DateChangerConstants.ERROR_TITLE, Messages.getErrorIcon());
            }
        }
    }
}