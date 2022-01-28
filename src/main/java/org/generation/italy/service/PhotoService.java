package org.generation.italy.service;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.generation.italy.model.Photo;
import org.generation.italy.model.PhotoForm;
import org.generation.italy.model.User;
import org.generation.italy.repository.PhotoRepository;
import org.generation.italy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {

	@Autowired
	private PhotoRepository repo;
	
	@Autowired
	private UserRepository userRepo;
	
	public Photo create(PhotoForm newPhoto, String username) throws IOException{
		Photo photo = new Photo();
		photo.setTitle(newPhoto.getTitle());
		if(newPhoto.getContent() != null) {
			byte[] contentSerialized = newPhoto.getContent().getBytes();
			photo.setContent(contentSerialized);
		}
		User loggedUser = userRepo.findByEmail(username).orElseThrow(()->new EntityNotFoundException());
		photo.setAuthor(loggedUser);
		return repo.save(photo);
	};
	
	public List<Photo> findAll(){
		return repo.findAll();
	};
	
	public Photo getById(Integer id) {
		return repo.getById(id);
	}
}
