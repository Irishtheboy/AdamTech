package za.co.admatech.service;


    
public interface IService <T, ID>{
    T create( T t);
    T create (T t);

    T read(ID id);
    T update(T t);
    boolean delete(ID id);
}

