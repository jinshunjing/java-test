package org.jim.powermock;

import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Override
    public int updateName(int id, String name) {
        return -1;
    }
}
