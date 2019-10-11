package migrator.app.domain.column;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleObjectProperty;
import migrator.app.domain.column.service.ColumnService;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.SimpleColumnProperty;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Table;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class ColumnServiceTest {
    protected ColumnService columnService;

    // @BeforeEach
    // public void setUp() {
    //     this.columnService = new SimpleColumnService(
    //         new ColumnFactory(),
    //         new ChangeService(
    //             new TableChangeFactory()
    //         ),
    //         new SimpleObjectProperty<>()
    //     );
    // }

    // @Test public void testSelectSetsSelectedValue() {
    //     this.columnService.select(
    //         this.columnService.getFactory()
    //             .createWithCreateChange("id")
    //     );

    //     assertEquals("id", this.columnService.getSelected().get().getName());
    // }

    // @Test public void testAddAddsColumnToList() {
    //     this.columnService.add(
    //         this.columnService.getFactory()
    //             .createWithCreateChange("id")
    //     );

    //     assertEquals(1, this.columnService.getList().size());
    //     assertEquals("id", this.columnService.getList().get(0).getName());
    // }

    // @Test public void testRemoveRemovesColumnFromListIfCreated() {
    //     Column column = this.columnService.getFactory()
    //         .createWithCreateChange("id");
    //     this.columnService.add(column);
    //     this.columnService.remove(column);

    //     assertEquals(0, this.columnService.getList().size());
    // }

    // @Test public void testRemoveMarkAsRemovedIfExisting() {
    //     Column column = this.columnService.getFactory()
    //         .createNotChanged("id", "string", "", false);
    //     this.columnService.add(column);
    //     this.columnService.remove(column);

    //     assertEquals(1, this.columnService.getList().size());
    //     assertEquals("delete", column.getChange().getCommand().getType());
    // }

    // @Test public void testSetAllSetsListValues() {
    //     Column column = this.columnService.getFactory()
    //         .createWithCreateChange("id");
    //     this.columnService.setAll(Arrays.asList(column));

    //     assertEquals(1, this.columnService.getList().size());
    //     assertEquals("id", this.columnService.getList().get(0).getName());
    // }
}