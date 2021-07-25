package com.example.demo.service;


import com.example.demo.dto.BookInfoDTO;
import com.example.demo.dto.BookRequestDTO;
import com.example.demo.dto.BookResponseDTO;
import com.example.demo.dto.SearchByPhraseDTO;
import com.example.demo.enums.FileFormat;
import com.example.demo.enums.SearchType;
import com.example.demo.exception.NoSuchBookException;
import com.example.demo.exception.UnsupportedFormatException;
import com.example.demo.model.Book;
import com.example.demo.model.FileInfo;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.FileInfoRepository;
import com.example.demo.utils.FileManager;
import com.example.demo.utils.ModelToDTOConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService{

    @Value("${number.book.onPage}")
    private int numberBookOnPage;

    private final BookRepository bookRepository;
    private final FileManager fileManager;
    private final FileInfoRepository fileInfoRepository;

    @Override
    @Transactional(readOnly = true,rollbackFor = {NoSuchBookException.class})
    public Book getBookById(long bookId) {
        return bookRepository.findById(bookId).orElseThrow(NoSuchBookException::new);
    }

    @Override
    @Transactional(rollbackFor = {IOException.class})
    public byte[] getContent(FileInfo bookContentFileInfo) {

        try {
            return fileManager.download(bookContentFileInfo.getKey());
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }

    }

    @Override
    @Transactional(rollbackFor = {IOException.class,UnsupportedFormatException.class})
    public boolean addBook(BookRequestDTO bookRequest){
        try {
            FileInfo image = fileInfoRepository.save(FileInfo.fileInfoFactory(bookRequest.getImage()));
            FileInfo content = fileInfoRepository.save(FileInfo.fileInfoFactory(bookRequest.getContent()));
            fileManager.upload(bookRequest.getImage().getBytes(),image.getKey());
            fileManager.upload(bookRequest.getContent().getBytes(),content.getKey());
            bookRepository.save(ModelToDTOConverter.castBookRequestDTOToBook(bookRequest,image,content));
        } catch (IOException |UnsupportedFormatException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    @Transactional
    public boolean updateBookInfo(long bookId, BookInfoDTO bookInfoDTO){
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if(optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setName(bookInfoDTO.getName());
            book.setPageNumber(bookInfoDTO.getPageNumber());
            book.setIsbn(bookInfoDTO.getIsbn());
            book.setIssuedDate(book.getIssuedDate());
            book.setDescription(bookInfoDTO.getDescription());
            book.setPublisher(bookInfoDTO.getPublisher());
            book.setGenres(bookInfoDTO.getGenres());
            book.setAuthors(bookInfoDTO.getAuthors());
            bookRepository.save(book);
            return true;
        }else{
            return false;
        }
    }
    @Override
    @Transactional(rollbackFor = {IOException.class,UnsupportedFormatException.class})
    public boolean updateBookFile(long bookId, MultipartFile file){
            try {
                Optional<Book> optionalBook = bookRepository.findById(bookId);
                if(!optionalBook.isPresent()) return false;
                Book book = optionalBook.get();
                FileInfo newFileInfo = FileInfo.fileInfoFactory(file);
                if(newFileInfo.getFormat().equals(FileFormat.PDF)){
                    fileManager.delete(book.getContent().getKey());
                    FileInfo updatedFileInfo = fileInfoRepository.save(updateFileInfo(book.getContent(),newFileInfo));
                    fileManager.upload(file.getBytes(),updatedFileInfo.getKey());
                }else{
                    fileManager.delete(book.getImage().getKey());
                    FileInfo updatedFileInfo = fileInfoRepository.save(updateFileInfo(book.getImage(),newFileInfo));
                    fileManager.upload(file.getBytes(),updatedFileInfo.getKey());
                }
                return true;
            } catch (IOException |UnsupportedFormatException e) {
                e.printStackTrace();
                return false;
            }

    }

    private FileInfo updateFileInfo(FileInfo oldFileInfo, FileInfo newFileInfo){
        oldFileInfo.setName(newFileInfo.getName());
        oldFileInfo.setFormat(newFileInfo.getFormat());
        oldFileInfo.setKey(newFileInfo.getKey());
        oldFileInfo.setSize(newFileInfo.getSize());
        oldFileInfo.setUploadDate(newFileInfo.getUploadDate());
        return oldFileInfo;
    }

    @Override
    @Transactional(rollbackFor = {IOException.class})
    public boolean deleteBook(long bookId){
        try {
            Optional<Book> book = bookRepository.findById(bookId);
            if(!book.isPresent()) return false;
            fileManager.delete(book.get().getImage().getKey());
            fileManager.delete(book.get().getContent().getKey());
            bookRepository.delete(book.get());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    @Transactional(readOnly = true)
    public Page<BookResponseDTO> selectByPhrase(SearchByPhraseDTO searchPhrase, int page) {
        Page<Book> books = searchPhrase.getSearchType().equals(SearchType.BOOK_NAME)?
                bookRepository.findByNameIsContaining(searchPhrase.getSearchPhrase(),PageRequest.of(page,numberBookOnPage)):
                bookRepository.findByAuthorsNameIsContaining(searchPhrase.getSearchPhrase(),PageRequest.of(page,numberBookOnPage));
        return ModelToDTOConverter.getBookResponseDTOS(books,fileManager);


    }


    @Override
    @Transactional(readOnly = true)
    public Page<BookResponseDTO> selectByGenre(String genreName, int page) {
        Page<Book> books = bookRepository.findByGenresName(genreName,PageRequest.of(page,numberBookOnPage));
        return ModelToDTOConverter.getBookResponseDTOS(books,fileManager);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookResponseDTO> selectByCharacter(Character character, int page) {
        Page<Book> books = bookRepository.findByNameStartsWith(character,PageRequest.of(page,numberBookOnPage));
        return ModelToDTOConverter.getBookResponseDTOS(books,fileManager);
    }



}
