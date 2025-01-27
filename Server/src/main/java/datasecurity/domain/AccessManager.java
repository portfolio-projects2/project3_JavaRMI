package datasecurity.domain;
import com.fasterxml.jackson.core.type.TypeReference;
import datasecurity.Enum.Role;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class AccessManager {

    private static final File resourceFile = new File("AccessControlList.json");
    public static ArrayList<Role> getAcceptedRoles(String operation) throws IOException {
        ArrayList<Role> acceptedRoles= new ArrayList<>();
        HashMap <Role,ArrayList<String>> rolesFromSource= loadRolesWithOperations();
        rolesFromSource.forEach((k,v)->{
            for (String op:(ArrayList<String>)v) {

            }
            if (v.contains(operation)){
                acceptedRoles.add(k);
            }
        });
        return acceptedRoles;
    }



    private static HashMap<Role,ArrayList<String>> loadRolesWithOperations() throws IOException {
        HashMap<Role,ArrayList<String>> rolesWithOperations=new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode dataFromJson = objectMapper.readTree(resourceFile);
        JsonNode roles = dataFromJson.get("roles");
        for (Iterator<String> it = roles.fieldNames(); it.hasNext(); ) {
            String role = it.next();
            String operations = roles.get(role).toString();
            ArrayList<String> permittedOperations = objectMapper.readValue(operations, new TypeReference<ArrayList<String>>() {});
            rolesWithOperations.put(Role.fromString(role),permittedOperations);
        }

        return rolesWithOperations;
    }


    public static ArrayList<Role> loadUserRoles(String referenceCookie) throws IOException {
        String username = Session.getSession(referenceCookie).getUser().getUsername();
        ArrayList<Role> userRoles= new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode dataFromJson = objectMapper.readTree(resourceFile);
        JsonNode usersArray = dataFromJson.get("users");
        for (JsonNode userNode : usersArray) {
            String sourceUsername = userNode.get("username").asText();
            if (username.equals(sourceUsername)){
                for (JsonNode role : userNode.get("roles")){
                    userRoles.add(Role.fromString(role.asText()));
                }

            }

        }
        return userRoles;
    }


}
