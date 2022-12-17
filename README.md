
# RequestReplyMessagingApp

*Αριστέα Λαχανά - 3590* 

**Server.java** 

Η κλάση Server έχει τη main.
Το πρώτο όρισμα (args[0]), το οποίο και χρησιμοποιείται, είναι το port_number.
Η main περιλαμβάνει τις κατάλληλες εντολές για τη σύνδεση των clients 
με τον server με RMI. 
 
**Client.java**

Η κλάση Client έχει επίσης main. Το πρώτο όρισμα (args[0]) είναι το ip, το
δεύτερο όρισμα (args[1]) είναι το port_number και τα υπόλοιπα ορίσματα
σχετίζονται με τα requests που κάνει ο κάθε client και χρησιμοποιούνται σε επόμενες κλάσεις. 
Η main περιλαμβάνει εντολές RMI για τη σύνδεση με τον server, καθώς και εντολές που συνθέτουν
το μήνυμα - request του client σε ένα StringBuilder. Στη συνέχεια καλούνται μέθοδοι για να ξεκινήσουν
οι ζητούμενες από το χρήστη λειτουργίες.


**Account**

Τα αντικείμενα της κλάσης Account είναι οι λογαριασμοί του κάθε χρήστη που 
αποθηκεύεται στον εξυπηρετητή. Τα private πεδία της κλάσης είναι τα username, authToken, messageBox και 
αρχικοποιούνται κατάλληλα. Στη κλάση υπάρχουν επίσης και μέθοδοι getters και setters
που χρησιμοποιούνται στη συνέχεια.


**Message**

Η κλάση Message παριστάνει ένα μήνυμα ενός χρήστη. Έχει τα private πεδία isRead, 
sender, receiver, body. Και σε αυτή τη κλάση υπάρχουν getters και setters που
χρησιμοποιούνται στη πορεία.


**MessagingServer**

Η MessagingServer είναι Interface που κάνει extend τη κλάση Remote. Περιλαμβάνει την υπογραφή των public μεθόδων
που ζητούνται για τις λειτουργίες που θέλει ο κάθε χρήστης.



**RemoteMessagingServer**

Η RemoteMessagingServer κάνει implement την MessagingServer. Περιλαμβάνει private πεδία,
το Map<Integer, Account> accounts, στο οποίο αποθηκεύονται όσοι λογαριασμοί δημιουργούνται, το 
int key που είναι μετρητής των αντικειμένων Account που βρίσκονται στο Map accounts, και το 
String text, το οποίο αρχικοποιείται από τη μέθοδο sendText(String) και έχει σαν τιμή το μήνυμα
που στέλνει ο χρήστης (τα args από τη main Client), και όσο τρέχει οποιαδήποτε από τις
μεθόδους που παριστάνει τη ζητούμενη λειτουργία, η τιμή του text αλλάζει. To text παίρνει σαν τιμή το
μήνυμα που πρέπει να εμφανιστεί στον χρήστη (reply), ανάλογα με το request που έκανε και με το πως
προχωράει αυτό που ζητήθηκε. Η μέθοδος getText() επιστρέφει τη τελική τιμή του text, με σκοπό να εμφανιστεί
στον χρήστη.


Στη συνέχεια χρησιμοποιείται η μέθοδος chooseMethod(), η οποία ανάλογα το FN_ID επιλέγει τη κατάλληλη
μέθοδο που αντιστοιχεί στη λειτουργία που ζήτησε ο χρήστης. Οι λειτουργίες αυτές υλοποιούνται από τις 
μεθόδους createAccount(String username), showAccounts(int authToken), sendMessage(int authToken, String recipient, String message_body),
showInbox(int authToken), readMessage(int authToken, int message_id), deleteMessage(int authToken, int message_id).


Τέλος, η κλάση περιλαμβάνει private μεθόδους που χρησιμοποιούνται από τις παραπάνω και δημιουργήθηκαν
για τη λύση κάποιων ζητημάτων τους. Αυτές οι μέθοδοι είναι οι List<Message> removeFromMessageBox(List<Message> messageBox, Message message),
Message testIfMessageExists(int message_id, List<Message> messageBox), boolean authTokenExists(int authToken), Account whichAccountByAuthToken(int authToken).




