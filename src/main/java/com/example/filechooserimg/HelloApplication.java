package com.example.filechooserimg;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HelloApplication extends Application {

    private final ImageView visorImagen = new ImageView();
    private File archivoActual;

    @Override
    public void start(Stage escenario) {
        BorderPane root = new BorderPane();

        //----Imagen----
        visorImagen.setPreserveRatio(true);
        root.setCenter(visorImagen);

        //----Menú(Clic Derecho)----
        ContextMenu menuContextual = new ContextMenu();
        MenuItem opcionGuardar = new MenuItem("Guardar imagen como...");
        opcionGuardar.setOnAction(e -> guardarImagen(escenario));
        menuContextual.getItems().add(opcionGuardar);

        visorImagen.setOnContextMenuRequested(e ->
                menuContextual.show(visorImagen, e.getScreenX(), e.getScreenY())
        );
        visorImagen.setOnMousePressed(e -> {
            if (menuContextual.isShowing()) {
                menuContextual.hide();
            }
        });

        //----Botones----
        Button botonAbrir = new Button("Abrir imagen");
        Button botonGuardar = new Button("Guardar como...");

        botonAbrir.setOnAction(e -> abrirImagen(escenario));
        botonGuardar.setOnAction(e -> guardarImagen(escenario));

        HBox zonaBotones = new HBox(10, botonAbrir, botonGuardar);
        root.setBottom(zonaBotones);

        escenario.setScene(new Scene(root));
        escenario.setTitle("FileChooser - Ejemplo con Imágenes");
        escenario.show();
    }

    private void abrirImagen(Stage escenario) {
        FileChooser selector = new FileChooser();
        selector.setTitle("Seleccionar imagen");
        selector.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes PNG", "*.png"),
                new FileChooser.ExtensionFilter("Imágenes JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("Todas las imágenes", "*.*")
        );

        File archivo = selector.showOpenDialog(escenario);
        if (archivo != null) {
            archivoActual = archivo;
            Image imagen = new Image(archivo.toURI().toString());
            visorImagen.setImage(imagen);
            visorImagen.setFitWidth(imagen.getWidth());
            visorImagen.setFitHeight(imagen.getHeight());
            escenario.sizeToScene();
        }
    }

    private void guardarImagen(Stage escenario) {
        if (archivoActual == null) return;

        FileChooser selector = new FileChooser();
        selector.setTitle("Guardar imagen como...");
        selector.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );

        File destino = selector.showSaveDialog(escenario);
        if (destino != null) {
            try {
                Files.copy(archivoActual.toPath(), destino.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
