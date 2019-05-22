/**
 * 
 */
package io.damelyngdoh.java.oneplusburstorganizer;

import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author Dame Lyngdoh
 *
 */
@SuppressWarnings("serial")
public class TableModel extends AbstractTableModel {
	
    private final List<Result> results;
	
    public TableModel(List<Result> results) {
        this.results = results;
    }
	
    public int getRowCount() {
        return this.results.size();
    }

    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int column) {
        String name = null;;
        switch(column) {
        case 0:
            name = "#";
            break;
        case 1:
            name = "File";
            break;
        case 2:
            name = "Directory";
            break;
        case 3:
            name = "Status";
            break;
        }
        return name;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if(this.results.isEmpty()) {
            return null;
        }

        Object result = null;

        switch(columnIndex) {
        case 0:
            result = (Integer)(rowIndex+1);
            break;
        case 1:
            result = this.results.get(rowIndex).getImageFile().getName();
            break;
        case 2:
            result = this.results.get(rowIndex).getDir().getPath();
            break;
        case 3:
            switch(this.results.get(rowIndex).getStatus()){
                case 1:
                    result = "Success";
                    break;
                case 2:
                    result = "Could not create directory";
                    break;
                case 3:
                    result = "Could not copy file to destination";
                    break;
                case 4:
                    result = "Copy complete but ile not deleted";
                    break;
            }
        }
        return result;
    }
}
