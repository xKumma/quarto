package be.kdg.integration2.mvpglobal.view.infoscreen;

import be.kdg.integration2.mvpglobal.model.BaseModel;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;

import java.io.BufferedReader;
import java.io.FileReader;


public class InfoScreenPresenter extends BasePresenter<InfoScreenView, BaseModel> {

    public InfoScreenPresenter(InfoScreenView view, BaseModel model) {
        super(view, model);

        view.getInfoText().setText(ReadInfoFromFile());
        view.getBtnOk().setOnAction(event -> view.getScene().getWindow().hide());
    }

    private String ReadInfoFromFile() {
        String infoTextInFile ="";
        try (BufferedReader reader = new BufferedReader(new FileReader(uiSettings.getInfoTextPath().toString()));){
            String line = "";
            String testString;
            while ((line = reader.readLine())!= null){
                infoTextInFile += line + "\n";
            }
        } catch (Exception ex) {
            // do nothing, if info.txt file can not be read or is incomplete, or ... a standard text will be return
        }
        return (infoTextInFile.compareTo("")==0)?"No info available":infoTextInFile;
    }
}
