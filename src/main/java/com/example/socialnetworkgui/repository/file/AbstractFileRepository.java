package com.example.socialnetworkgui.repository.file;


import com.example.socialnetworkgui.domain.Entity;
import com.example.socialnetworkgui.domain.validators.Validator;
import com.example.socialnetworkgui.repository.memory.InMemoryRepository;

import java.io.*;
import java.util.Arrays;
import java.util.List;


///Aceasta clasa implementeaza sablonul de proiectare Template Method; puteti inlucui solutia propusa cu un Factori (vezi mai jos)
public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID,E> {
    String fileName;
    public AbstractFileRepository(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName=fileName;
        loadData();

    }

    private void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linie;
            while((linie=br.readLine())!=null){
                if (linie.length() > 0) {
                    List<String> attr=Arrays.asList(linie.split(";"));
                    E e=extractEntity(attr);
                    super.save(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void resetFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.flush();
            for (E enity : super.findAll()) {
                bw.write(createEntityAsString(enity));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  extract entity  - template method design pattern
     *  creates an entity of type E having a specified list of @code attributes
     * @param attributes
     * @return an entity of type E
     */
    public abstract E extractEntity(List<String> attributes);

    protected abstract String createEntityAsString(E entity);

    protected void writeToFile(E entity){
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(fileName,true))) {
            bW.write(createEntityAsString(entity));
            bW.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean save(E entity){
        boolean result = super.save(entity);
        if (result)
        {
            writeToFile(entity);
        }
        return result;

    }

    @Override
    public boolean update(E entity) {
        boolean result = super.update(entity);
        if (result) {
            resetFile();
        }
        return result;
    }

    @Override
    public E delete(ID id) {
        E entity = super.delete(id);
        if (entity != null) {
            resetFile();
        }
        return entity;
    }




}

