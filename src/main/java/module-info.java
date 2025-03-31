module com {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.perko.bookshelf.model;
    opens com.perko.bookshelf.model to javafx.fxml;
    exports com.perko.bookshelf.view;
    opens com.perko.bookshelf.view to javafx.fxml;
    exports com.perko.bookshelf.application;
    opens com.perko.bookshelf.application to javafx.fxml;
}