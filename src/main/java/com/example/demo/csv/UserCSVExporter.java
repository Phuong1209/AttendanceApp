package com.example.demo.csv;

import com.example.demo.dto.UserCSVDTO;
import com.example.demo.dto.UserDTO;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class UserCSVExporter {

    private List<UserCSVDTO> listUsers;

    public UserCSVExporter(List<UserCSVDTO> listUsers) {
        this.listUsers = listUsers;
    }

    public void generateCSV(HttpServletResponse response) throws IOException{
        CSVWriter writer = new CSVWriter(response.getWriter());

        //Write header to CSV file
        writer.writeNext(new String[]{"id","userName", "userFullName", "position", "department"});

        // Write data to CSV file
        for (UserCSVDTO userDTO : listUsers){
            writer.writeNext(new String[] {String.valueOf(userDTO.getId()),userDTO.getUserName(),userDTO.getUserFullName(),userDTO.getPositions(),userDTO.getDepartments()});
        }
        writer.close();
    }
}
