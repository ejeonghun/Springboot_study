package com.example.myschool.teacher.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.myschool.teacher.domain.Teacher;
import com.example.myschool.teacher.dto.TeacherDto;
import com.example.myschool.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TeacherService {
	private final TeacherRepository teacherRepository;

	public List<TeacherDto> getTeachers(String name) {
		ArrayList<TeacherDto> teachers = new ArrayList<TeacherDto>();
		if (name == null) {
			teacherRepository.findAll().forEach(t -> {
				TeacherDto teacherDto = TeacherDto.builder()
						.teacherId(t.getTeacherId())
						.name(t.getName())
						.description(t.getDescription())
						.build();
				teachers.add(teacherDto);
				// teachers.add(new TeacherDto(t.getTeacherId(), t.getName(), t.getDescription()));
				// 너무 길어서 가독성이 떨어지기 때문에 build 하여서 가독성 향상
			});
		} else {
			teacherRepository.findByNameContainsIgnoreCase(name).forEach(t -> {
				teachers.add(new TeacherDto(t.getTeacherId(), t.getName(), t.getDescription()));
			});
		}

		return teachers;
	}

	public TeacherDto getTeacher(int id) {
		Optional<Teacher> result = teacherRepository.findById(id);
		if (result.isPresent()) {
			Teacher t = result.get();
			return new TeacherDto(t.getTeacherId(), t.getName(), t.getDescription());
		} else {
			return null;
		}
	}
}