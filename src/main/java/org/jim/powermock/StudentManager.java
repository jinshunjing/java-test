package org.jim.powermock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentManager {
    @Autowired
    private StudentService studentService;

    public int updateName(int id, String name) {
        int result = studentService.updateName(id, name);
        return result;
    }

}
