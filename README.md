# PATIENT CLIENT API for WOLFFGRAM: wolff_patient

**USER MANUAL**

We have designed an application to monitor the Wolff Parkinson white syndrome. The application allows the patient to send signs and symptoms that are relevant for the diagnosis of the disease.

It will also allow him/her to send physiological parameters using the Bitalino device, to which the application is connected.

The Wolff Parkinson white syndrome is a disorder of the electrical system of the heart. About 40% of people who suffer from it do not present symptoms; some of them are an abnormally fast heartbeat, palpitations, shortness of breath, syncope.

The cause is unknown; some cases are due to a mutation which may be inherited. This mutation leads to an accessory electrical conduction pathway between the atria and the ventricles.

The diagnosis can be done when we observe in the electrocardiogram (ECG) a short PR interval and a delta wave. It is a type of pre-excitation syndrome.

This syndrome affects between the 0.1 and 0.3% in the population. The risk of death in those without symptoms is about 0.5% per year in children and 0.1% per year in adults. WPW is also associated with a very small risk of sudden death due to more dangerous heart rhythm disturbances.

Our aim with this project is to provide a way to detect this unknown disease in an easier way, facilitating the measuring of the ECG to the patient. He can do it at home, saving him visits to the hospital and allowing him to do the measuring more frequently, which is better to monitor the disorder and put him in the right treatment. It allows a constant following of the development of the disease in a more continuous way which is also better for reducing the symptoms.

The patient can also write down his symptoms at every moment he wants to record a new ECG, which also enables the doctor to have a continuous tracking of the state of the patient.

**WOLLGRAM APP USAGE**

With the Server being open, we can run the clients app. Here, **wolff\_patient** usage is explained.

A Welcome window will appear with the logo of our app, &quot; **Wolffgram**&quot;. In this window the user has different options.


At the bottom left, the patient can click on the settings icon, which is a nut icon, in order to add the IP address where the server is running and also to add the MAC address of the bitalino device that is going to be used to record the ECG (if desired). After filling up the settings, the Save button must be pushed to save the data and return to the welcome page. The user can also press the X button and return to the original page.

If the patient tries to access without previously saving an IP address, an alert will inform the user that at least the IP address must be introduced.

He/she can log in by putting its username/DNI/ID and password if he has already signed up. If it is the patient&#39;s first time in the application, he can click on the Sign up button on the bottom right and a new scene will appear with different boxes, where the user can add all the personal information: its ID number, password ( **not stored in plain text** ), name, surname, gender, date of birth, social security number, address and telephone. After pressing &quot;create account&quot;, all data will be saved and stored in the server. When we have finished creating the account, we can access the main menu after logging in.


In the Main Menu window, the patient can see at the top left his/her name and at the top right there is the logout button. If this is pressed, the program will close correctly (closing the program with the X button will also close it properly).

Three different icons are also present in the main menu window.

- My profile.
- Medical records.
- Information.


Depending on the action the patient wants to do, a different icon must be selected.

**Information:**

The user can find useful information.


The first window gives information about how to use the program in case the user is a bit lost (although the app was designed in a simple and intuitive way to avoid this). The second window is about how the electrodes must be placed to record the ECG with the Bitalino and avoid future errors in the measurement of the signal.

Finally, the third and last window contains some information about us and about the aim of this project.


**My Profile:**

Here the user has access to all his actual data and, he can change and update his personal information as the name, the surname, the password, address…

By clicking on the update button, the data will be saved, and the new attributes will be shown on screen.

If the patient wants to change the password, he must enter the old password and the new password and then click on the change password button.

After making the changes, the user can go back to the main menu by using the &quot;back to menu&quot; button or by the X of the window.

**Medical Records:**

Here the patient can visualize a table with all the medical recordings that have been done before. For each medical record, many attributes are shown: the date when it was recorded and the different symptoms that were and not present. A system for **displaying visually an electrocardiogram** has been developed, so the user can select the &quot;View&quot; button on the ECG column to see the electrocardiogram if it was recorded.

Next to that column, the comment section can be found: the doctor can add any observation about the symptoms or the ECG, as a way to **communicate with the patient** and give feedback to each medical record.

The &quot;Add Clinical Record&quot; button can be selected to start a new record. A new window will appear asking the patient if he wants to record and ECG or not.


If the &quot;No&quot; is selected, a medical form about the symptoms like the presence or absence of dizziness or palpitations must be answered. Some extra information about the state of the patient can be added if he considers that it is necessary (like any symptom or observation that has not been asked) but it is not compulsory.


Click &quot;Save Record&quot; to store the data in the server, and then the Medical History window will show with a new row where the new medical record appears.

In case we select &quot;Yes&quot; to record the ECG, the Bitalino window will show up. Here, there are four different actions that can be performed.


First, it is necessary to connect the Bitalino by introducing the MAC Address of the device (if it was not introduced before), the user will find this information in the back part of the Bitalino. If any Bitalino device is not connected, it will not be possible to record any ECG (the app has been programmed in order to **manage any connection issue** with the Bitalino and inform the user if needed, like introducing a wrong MAC address or connection loss with the Bitalino just before or while recording

There are two possibilities of how to record the ECG:

- The option of Record **automatic ECG** : in this option the patient must specify the number of seconds corresponding to the duration of the future recording. The program will start and stop the recording automatically.
- The option of Record **manual ECG** : the patient here needs to indicate where to stop recording whenever he wants.


By pressing Save ECG, it will be saved, as its name indicates.

Press &quot;go back&quot; to return to the medical history window and visualize the new record just updated.


We can exit our program pressing the log out button, or once we are in the main menu pressing the x button of the window.

Another extra in our project is the **doctor project** ; this will also be connected to the server; if the server is not connected the Doctor will not be able to connect, works the same way as the patient.


Once the server is open, the doctor can connect to it. When we first run the doctor, a welcome window will appear where the password is asked to the doctor. Before writing it, the doctor must click on the settings button on the right, in order to introduce the IP address. After doing that, the doctor can now write the password (password=doctor by default) and press enter to have **access to all the information of the different patients**.


In this table the doctor can visualize the different patients and their personal data. By clicking on the button view, the medical history will all the recordings of that patient will pop up. An interesting and implemented function is that the doctor has a **Doctor comment option** , where the doctor can add information or give feedback to the patient based on the previous information; and also, the doctor can check the data of each ECG done by the patient.


From the medical history of the patient, the doctor can go back to the main menu in case he/she wants to check other patients and by pressing the logout button or by clicking on the x of the window, the doctor will close the program.

In both cases we assure the user a perfect connection to the server at any time, checking the connection between them. If the server fails **, we have considered these connection errors in order to manage them and inform him/her**.

When that happens, **the client (patient/doctor) will be notified, and information won&#39;t be updated until the connection is re-established** and the user logs in again.
