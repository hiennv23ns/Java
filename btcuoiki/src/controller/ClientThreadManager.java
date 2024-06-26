package controller;

import java.util.ArrayList;
import java.util.List;

public class ClientThreadManager {
    private List<ClientThread> clientThreads;

    public ClientThreadManager() {
        clientThreads = new ArrayList<ClientThread>();
    }
    
    public boolean add(ClientThread clientThread) {
        if (!this.clientThreads.contains(clientThread)) {
            clientThreads.add(clientThread);
            return true;
        }
        return false;
    }
    
    public void remove(ClientThread clientThread) {
        if (clientThreads.contains(clientThread)) {
            clientThreads.remove(clientThread);
        }
    }
    
    public List<ClientThread> getClientThreads() {
        return clientThreads;
    }

    public void setClientThreads(List<ClientThread> clientThreads) {
        this.clientThreads = clientThreads;
    }

    public boolean isLogedIn(String username) {
        if (clientThreads.size() == 0) {
            return false;
        }
        for (ClientThread ct : clientThreads) {
            if (ct.getUserModel() != null) {
                if (ct.getUserModel().getUserName().equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public ArrayList<String> getOnlineList() {
        ArrayList<String> onlineList = new ArrayList<>();
        
//       
        for (ClientThread ct : clientThreads) {
			if(ct.getUserModel()!=null) {
				onlineList.add(ct.getUserModel().getFullName());
			}
		}
        if (!onlineList.isEmpty()) {
            return onlineList;  //{"nam","nu"};
        } else {
            return null ;
           
        }
    }
    
    public void broadcast(String data) {
        clientThreads.forEach(c -> c.sendDataToClient(data));
    }
}