import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


    class PhoneBookFields {

        private String name;
        private String email;
        private Date timeCreated;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Date getTimeCreated() {
            return timeCreated;
        }

        public void setTimeCreated(Date timeCreated) {
            this.timeCreated = timeCreated;
        }
    }

    class TrieNode {
        HashMap<Character, TrieNode> child;
        boolean isLast;

        public TrieNode() {
            child = new HashMap<Character, TrieNode>();
            for (char i = 'a'; i <= 'z'; i++)
                child.put(i, null);
            isLast = false;
        }

    }

    class Trie {
        TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        // Inserts Phonebook Contact into the Trie
        public void insert(String s) {
            int len = s.length();

            // 'iterate' is used to iterate the Trie Nodes
            TrieNode iterate = root;

            for (int i = 0; i < len; i++) {

                // Check if the s[i] is already present in the Trie
                TrieNode nextNode = iterate.child.get(s.charAt(i));

                if (nextNode == null) {
                    // If not found then a new TrieNode would be created
                    nextNode = new TrieNode();
                    // Insert into the HashMap
                    iterate.child.put(s.charAt(i), nextNode);
                }

                // Move the iterator('iterate') ,to point to next Trie Node
                iterate = nextNode;

                // If its the last character of the string 's' then mark 'isLast' as true
                if (i == len - 1) {
                    iterate.isLast = true;
                }

            }
        }

        // This method displays all contacts going through current node.
        // String 'prefix' is the string corresponding to the path from
        // root to curNode.
        public void displayContactsUtil(TrieNode curNode,
                                        String prefix,
                                        ArrayList<String> contactsWithPrefix) {
            // Check if the string 'prefix' ends at this particular Node
            // If it ends at this node, then the string found so far is displayed.
            if (curNode.isLast) {
                contactsWithPrefix.add(prefix);
            }

            // Now, we find all adjacent Nodes to current  Node and
            // then, the method is called recursively.
            for (char i = 'a'; i <= 'z'; i++) {
                TrieNode nextNode = curNode.child.get(i);
                if (nextNode != null) {
                    displayContactsUtil(nextNode, prefix + i, contactsWithPrefix);
                }
            }
        }

        // THis method displays suggestions for each character entered by
        // the user for a given string 'str'
        void getContacts(String str,HashMap<String,PhoneBookFields> phonebook) {

            TrieNode prevNode = root;
            String prefix = "";
            int len = str.length();
            // For each entry of the characters, display the contact List for string formed.
            int i;

            for (i = 0; i < len; i++) {
                // 'str' stores the entry so far
                prefix += str.charAt(i);

                // Get last character entry
                char lastChar = prefix.charAt(i);

                // Find Node corresponding to the last entry of 'str'
                // which is pointed to by prevNode of the Trie
                TrieNode curNode = prevNode.child.get(lastChar);

                // If nothing is found, then break the loop as no more present prefixes.
                if (curNode == null) {
                    i++;
                    break;
                }

                ArrayList<String> contactsWithPrefix  = new ArrayList<>();

                displayContactsUtil(curNode, prefix,contactsWithPrefix);

                contactsWithPrefix.forEach(contact ->{
                    System.out.println("\n Phone Number: "+ contact + " has the following characteristics. "
                            + "\n Name: " + phonebook.get(contact).getName()
                            + "\n Email: " + phonebook.get(contact).getEmail()
                            + "\n Time Created: " + phonebook.get(contact).getTimeCreated()
                            + "\n --------------------------------------"
                    );
                });

                // Change prevNode for next prefix

                prevNode = curNode;
            }
        }
    }


    class PhoneBookDirectory {
        HashMap<String, PhoneBookFields> phonebook;
        Trie trie;

        public PhoneBookDirectory() {
            phonebook = new HashMap<>();
            trie = new Trie();
        }

        public void addContact(String number, PhoneBookFields phoneno) {
            if(phonebook.get(number) == null) {
                phonebook.put(number, phoneno);
                trie.insert(number);
            }
            else {
                System.out.println(" The Number:" + number + " already exists, " +
                        " hence the name " + phoneno.getName() + " would be skipped. " +
                        " Remember, Number must be unique");
            }
        }

        public void searchForAllCombination(String query) {
            trie.getContacts(query,phonebook);
        }
    }


    // Driver code
    class PhoneBook {
        public static void main(String args[]) {
            PhoneBookDirectory directory = new PhoneBookDirectory();

            PhoneBookFields first = new PhoneBookFields();
            first.setEmail("first@email.com");
            first.setName("First Number");
            first.setTimeCreated(new Date());
            directory.addContact("1",first);

            PhoneBookFields second = new PhoneBookFields();
            second.setEmail("second@email.com");
            second.setName("Second Number");
            second.setTimeCreated(new Date());
            directory.addContact("1",second);

            PhoneBookFields third = new PhoneBookFields();
            third.setEmail("third@email.com");
            third.setName("Third Number");
            third.setTimeCreated(new Date());
            directory.addContact("123", third);

            PhoneBookFields fourth = new PhoneBookFields();
            fourth.setEmail("fourth@email.com");
            fourth.setName("Fourth Number");
            fourth.setTimeCreated(new Date());
            directory.addContact("1234",fourth);

            String query = "1234";
            directory.searchForAllCombination(query);
        }
    }
