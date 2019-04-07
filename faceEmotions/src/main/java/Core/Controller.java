package Core;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/** The Controller class is connected with the faceEmotions.fxml file,
 **  it handles slider events and face changes. Also it implements the method model.start()
 **  and also sends parameters to the ModelGenerator class. There is a listener toogle, in the init method,
 **  Upon certain conditions in the slider, the face changes accordingly.
 **/

public class Controller implements Initializable {


    private static Model model;
    private static Model model1;
    private static Model model2;
    private static Model model3;
    private static Model model4;
    private final int PORT = 1596;
    Text val;
    String one = "100";
    @FXML
    Label portNumber;
    @FXML
    JFXToggleButton toggle;

    @FXML
    JFXSlider agreement;
    @FXML
    JFXSlider disagreement;
    @FXML
    JFXSlider frustate;
    @FXML
    JFXSlider concentrate;
    @FXML
    JFXSlider thinking;
    @FXML
    JFXSlider unsure;

    @FXML
    QuadCurve smile;

    @FXML
    Ellipse rightEye;

    @FXML
    Ellipse leftEye;

    @FXML
    Line nose;

    @FXML
    Ellipse face;

    @FXML
    ProgressIndicator aggrementIndicator;

    @FXML
    ProgressIndicator disagreementIndicator;

    @FXML
    ProgressIndicator frustateIndicator;

    @FXML
    ProgressIndicator concentrateIndicator;

    @FXML
    ProgressIndicator thinkingIndicator;

    @FXML
    ProgressIndicator unsureIndicator;

    @FXML
    Label serverRunning;

    @FXML
    javafx.scene.image.ImageView thumbsUp;

    @FXML
    Line faceupper;

    @FXML
    javafx.scene.image.ImageView thumbsDown;

    @FXML
    Button reset;

    @FXML
    Circle leftEyeBall;

    @FXML
    Circle rightEyeBall;




    @FXML void sliderAgreementDragged(){
        System.out.println((double)(agreement.getValue()));

        aggrementIndicator.progressProperty().setValue(agreement.getValue());

        if(agreement.getValue() ==  1) {
            val = (Text) aggrementIndicator.lookup(".percentage");
            val.setText(one);
            aggrementIndicator.progressProperty().setValue(agreement.getValue());

        }
        if(agreement.getValue() >= 0.7) {
            File file = new File("res/thumbsUp.png");
            Image image = new Image(file.toURI().toString());
            thumbsUp.setVisible(true);
            thumbsUp.setImage(image);
        }
        else{
            thumbsUp.setVisible(false);
        }

        DataGenerator dataGen = new DataGenerator();
        dataGen.updateParam("agreement", (double)agreement.getValue() * 10 );
    }

    @FXML void sliderDisagreementDragged(){
        System.out.println(disagreement.getValue());
        if(disagreement.getValue() < 1) {
            disagreementIndicator.progressProperty().setValue(disagreement.getValue());
        }
        else{
            Text text =  (Text) disagreementIndicator.lookup(".percentage");
            disagreementIndicator.progressProperty().setValue(disagreement.getValue());
            if(text!=null && text.getText().equals("Done")){
                text.setText(one);
            }
        }
        if(disagreement.getValue() >= 0.7) {
            File file = new File("res/thumbsDown.png");
            Image image = new Image(file.toURI().toString());
            thumbsDown.setVisible(true);
            thumbsDown.setImage(image);
            faceupper.setVisible(false);
            smile.setRotate(360.0);
            smile.setControlX(50.0);
            smile.setControlY(-43.0);
        }
        else if (disagreement.getValue() >= 0.3 && disagreement.getValue() < 0.7){
            thumbsDown.setVisible(false);
            faceupper.setVisible(true);
            smile.setRotate(180.0);
            smile.setControlY(0.0);
            smile.setControlX(0.0);
        }
        else{
            thumbsDown.setVisible(false);
            faceupper.setVisible(true);
            smile.setRotate(180.0);
            smile.setControlX(50.0);
            smile.setControlY(-43.0);
        }
        DataGenerator dataGen = new DataGenerator();
        dataGen.updateParam("disagreement", (double)disagreement.getValue() * 10 );
    }

    @FXML void sliderFrustateDragged(){
        System.out.println(frustate.getValue());
        if(frustate.getValue() < 1) {
            frustateIndicator.progressProperty().setValue(frustate.getValue());
        }
        else{
            Text text =  (Text) frustateIndicator.lookup(".percentage");
            frustateIndicator.progressProperty().setValue(frustate.getValue());
            if(text!=null && text.getText().equals("Done")){
                text.setText(one);
            }
        }
        if(frustate.getValue() >= 0.7) {
            rightEye.setFill(Color.valueOf("#cc3434"));
            leftEye.setFill(Color.valueOf("#cc3434"));
            leftEyeBall.setLayoutX(82.0);
            rightEyeBall.setLayoutX(172.0);
            smile.setRotate(180.0);
            smile.setControlY(0.0);
            smile.setControlX(0.0);
            face.setFill(Color.valueOf("#e24165"));

        }
        else if (frustate.getValue() >= 0.3 && frustate.getValue() < 0.7){
            rightEye.setFill(Color.WHITE);
            leftEye.setFill(Color.WHITE);
            smile.setRotate(180.0);
            smile.setControlY(0.0);
            smile.setControlX(0.0);
            leftEyeBall.setLayoutX(72.0);
            rightEyeBall.setLayoutX(161.0);
            face.setFill(Color.valueOf("#ffc1bf"));
        }
        else{
            rightEye.setFill(Color.WHITE);
            leftEye.setFill(Color.WHITE);
            smile.setFill(Color.WHITE);
            smile.setControlY(-43);
            smile.setControlX(50);
            leftEyeBall.setLayoutX(72.0);
            rightEyeBall.setLayoutX(161.0);
            face.setFill(Color.valueOf("#ffe0bd"));


        }
        DataGenerator dataGen = new DataGenerator();
        dataGen.updateParam("frustrate", (double)frustate.getValue() * 10);
    }
    @FXML void sliderConcentrateDragged(){
        System.out.println(concentrate.getValue());
        if(concentrate.getValue() < 1) {
            concentrateIndicator.progressProperty().setValue(concentrate.getValue());
        }
        else{
            Text text =  (Text) concentrateIndicator.lookup(".percentage");
            concentrateIndicator.progressProperty().setValue(concentrate.getValue());
            if(text!=null && text.getText().equals("Done")){
                text.setText(one);
            }
        }
        if(concentrate.getValue() >= 0.7) {
            leftEye.setRadiusY(14.0);
            rightEye.setRadiusY(14.0);
            leftEyeBall.setRadius(14.0);
            rightEyeBall.setRadius(14.0);
        }
        else if (concentrate.getValue() >= 0.3 && concentrate.getValue() < 0.7) {
            leftEye.setRadiusY(10.0);
            rightEye.setRadiusY(10.0);
            leftEyeBall.setRadius(10.0);
            rightEyeBall.setRadius(10.0);

        }
        else{
            leftEye.setRadiusY(9.0);
            rightEye.setRadiusY(9.0);
            leftEyeBall.setRadius(8.0);
            rightEyeBall.setRadius(8.0);

        }
        DataGenerator dataGen = new DataGenerator();
        dataGen.updateParam("concentrate", (double)concentrate.getValue() * 10 );
    }
    @FXML void sliderThinkingDragged(){
        System.out.println(thinking.getValue());
        if(thinking.getValue() < 1) {
            thinkingIndicator.progressProperty().setValue(thinking.getValue());
        }
        else{
            Text text =  (Text) thinkingIndicator.lookup(".percentage");
            thinkingIndicator.progressProperty().setValue(thinking.getValue());
            if(text!=null && text.getText().equals("Done")){
                text.setText(one);
            }
        }
        if(thinking.getValue() >= 0.7) {
            nose.setEndY(0.0);
        }
        else if (thinking.getValue() >= 0.3 && thinking.getValue() < 0.7) {
            nose.setEndY(-2.0);
        }

        else{
            nose.setEndY(-4.0);
        }
        DataGenerator dataGen = new DataGenerator();
        dataGen.updateParam("thinking", (double)thinking.getValue() * 10 );
    }
    @FXML void sliderUnsureDragged(){
        System.out.println(unsure.getValue());
        if(unsure.getValue() < 1) {
            unsureIndicator.progressProperty().setValue(unsure.getValue());
        }
        else{
            Text text =  (Text) unsureIndicator.lookup(".percentage");
            unsureIndicator.progressProperty().setValue(unsure.getValue());
            if(text!=null && text.getText().equals("Done")){
                text.setText(one);
            }
        }
        if(unsure.getValue() >= 0.7) {
            nose.setEndY(0.0);
            smile.setRotate(180.0);
            smile.setControlY(0.0);
            smile.setControlX(0.0);
        }
        else if (unsure.getValue() >= 0.3 && unsure.getValue() < 0.7) {
            nose.setEndY(-2.0);
        }

        else{
            smile.setRotate(180.0);
            smile.setControlX(50.0);
            smile.setControlY(-43.0);
        }
        DataGenerator dataGen = new DataGenerator();
        dataGen.updateParam("unsure", (double)unsure.getValue() * 10);
    }

    public void Reset() {

        face.setFill(Color.valueOf("#ffe0bd"));
        thumbsDown.setVisible(false);
        thumbsUp.setVisible(false);
        faceupper.setVisible(true);
        smile.setRotate(180.0);
        smile.setControlX(50.0);
        smile.setControlY(-43.0);
        leftEye.setFill(Color.WHITE);
        rightEye.setFill(Color.WHITE);
        smile.setFill(Color.WHITE);
        agreement.setValue(0.0);
        disagreement.setValue(0.0);
        frustate.setValue(0.0);
        concentrate.setValue(0.0);
        thinking.setValue(0.0);
        unsure.setValue(0.0);
        aggrementIndicator.progressProperty().setValue(agreement.getValue());
        disagreementIndicator.progressProperty().setValue(disagreement.getValue());
        frustateIndicator.progressProperty().setValue(frustate.getValue());
        concentrateIndicator.progressProperty().setValue(concentrate.getValue());
        thinkingIndicator.progressProperty().setValue(thinking.getValue());
        unsureIndicator.progressProperty().setValue(unsure.getValue());
        leftEyeBall.setLayoutX(72.0);
        rightEyeBall.setLayoutX(161.0);
        leftEye.setRadiusY(9.0);
        rightEye.setRadiusY(9.0);
        leftEyeBall.setRadius(8.0);
        rightEyeBall.setRadius(8.0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        aggrementIndicator.setStyle("-fx-progress-color: #086f18 ;");
        disagreementIndicator.setStyle("-fx-progress-color: #086f18 ;");
        frustateIndicator.setStyle("-fx-progress-color: #086f18 ;");
        concentrateIndicator.setStyle("-fx-progress-color: #086f18 ;");
        unsureIndicator.setStyle("-fx-progress-color: #086f18 ;");
        thinkingIndicator.setStyle("-fx-progress-color: #086f18 ;");
        portNumber.setText(Integer.toString(PORT));

        toggle.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(toggle.isSelected() == true){
                    serverRunning.setVisible(true);
                    model = new Model(new DataGenerator(), new Publisher(PORT));
                    model1 = new Model(new DataGenerator(), new Publisher(PORT+1));
                    model2 = new Model(new DataGenerator(), new Publisher(PORT+2));
                    model3 = new Model(new DataGenerator(), new Publisher(PORT+3));
                    model4 = new Model(new DataGenerator(), new Publisher(PORT+4));
                    model.start();
                    model1.start();
                    model2.start();
                    model3.start();
                    model4.start();
                    toggle.setText("STOP");

                }
                else{
                    //JOptionPane.showMessageDialog(null,"Server Stopped");
                    model.stop();
                    model1.stop();
                    model2.stop();
                    model3.stop();
                    model4.stop();
                    serverRunning.setVisible(false);
                    toggle.setText("RUN");
                }
            }
        });

    }

}
