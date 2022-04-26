package ru.qwonix.suai.airporter.fill;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Filler {
/*
    private static final int count = 1;

    private static final String api = "https://api.randomdatatools.ru/?count=" + count;

    private final EmployeeDAO employeeDAO;
    private final PassengerDAO passengerDAO;

    public Filler(EmployeeDAO employeeDAO, PassengerDAO passengerDAO) {
        this.employeeDAO = employeeDAO;
        this.passengerDAO = passengerDAO;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void Employees() {
        JSONArray jsonArray;
        log.info("EventListener begin");
        try {
            jsonArray = JSONDownloader.readJsonArrayFromUrl(api);
        } catch (IOException e) {
            log.error("api request error");
            return;
        }
        Random random = new Random();
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Employee employee = new Employee();
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String lastName = jsonObject.getString("LastName");
            String firstName = jsonObject.getString("FirstName");
            String fatherName = jsonObject.getString("FatherName");
            String dateOfBirth = jsonObject.getString("DateOfBirth");
            String phone = jsonObject.getString("Phone");
            String genderCode = jsonObject.getString("GenderCode");
            String pasportNum = jsonObject.getString("PasportNum");
            String address = jsonObject.getString("Address");

            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setFatherName(fatherName);
            employee.setDateOfBirth(LocalDate.parse(dateOfBirth
                    , DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            employee.setPhone(phone);
            employee.setGenderCode(Gender.valueOf(genderCode).db_name);
            employee.setPasportNum(pasportNum);
            employee.setAddress(address);

            employee.setPositionId(1);
            employee.setSalary(random.nextInt(20_000) + 20_000);

            employees.add(employee);
        }

        log.info("EventListener json end");
        System.out.print(employees);

        employeeDAO.saveAll(employees);
        log.info("EventListener end");
    }

//    @EventListener(ContextRefreshedEvent.class)
    public void Passengers() {
        JSONArray jsonArray;
        log.info("EventListener begin");
        try {
            jsonArray = JSONDownloader.readJsonArrayFromUrl(api);
        } catch (IOException e) {
            log.error("api request error");
            return;
        }

        List<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Passenger passenger = new Passenger();
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String login = jsonObject.getString("Login");
            String password = jsonObject.getString("Password");
            String lastName = jsonObject.getString("LastName");
            String firstName = jsonObject.getString("FirstName");
            String fatherName = jsonObject.getString("FatherName");
            String dateOfBirth = jsonObject.getString("DateOfBirth");
            String genderCode = jsonObject.getString("GenderCode");
            String phone = jsonObject.getString("Phone");
            String email = jsonObject.getString("Email");
            String pasportNum = jsonObject.getString("PasportNum");

            passenger.setFirstName(firstName);
            passenger.setLastName(lastName);
            passenger.setFatherName(fatherName);
            passenger.setDateOfBirth(LocalDate.parse(dateOfBirth
                    , DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            passenger.setPhone(phone);
            passenger.setLogin(login);

            String password1 = sha1(password);

            passenger.setPassword(password1);
            passenger.setEmail(email);
            passenger.setGenderCode(Gender.valueOf(genderCode).db_name);
            passenger.setPasportNum(pasportNum);


            passengers.add(passenger);
        }

        passengerDAO.saveAll(passengers);
        log.info("EventListener end");
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String test = "238a1843d81dd7fbe80b5c1b99515c4ba8c94d0d";

        System.out.println(test);
        System.out.println(sha1("roman"));

    }


    private static String sha1(String inputString) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("sha-1");
        } catch (NoSuchAlgorithmException ignore) {
        }
        final byte[] hash = digest.digest(inputString.getBytes(StandardCharsets.UTF_8));
        final StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            final String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }*/
}
