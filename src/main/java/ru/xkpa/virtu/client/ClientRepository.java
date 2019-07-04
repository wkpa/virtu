package ru.xkpa.virtu.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Pavel Kurakin
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("select c from Client c " +
            "where (:name is null or lower(c.name) like :name)" +
            " and (:surname is null or lower(c.surname) like :surname)" +
            " and (:patronymic is null or lower(c.patronymic) like :patronymic)")
    List<Client> findClientByNames(@Param("name") String name,
                                   @Param("surname") String surname,
                                   @Param("patronymic") String patronymic);
}
