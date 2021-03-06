package ir.pint.soltoon.soltoongame.server;

import ir.pint.soltoon.soltoongame.shared.data.map.Cell;
import ir.pint.soltoon.soltoongame.shared.data.map.GameBoard;
import ir.pint.soltoon.soltoongame.shared.data.map.GameObject;
import ir.pint.soltoon.soltoongame.shared.result.AgentDamagedEvent;
import ir.pint.soltoon.soltoongame.shared.result.AgentDiedEvent;
import ir.pint.soltoon.utils.shared.facades.json.SecureConvert;
import ir.pint.soltoon.utils.shared.facades.result.ResultStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by amirkasra on 9/30/2017 AD.
 */

@SecureConvert(GameBoard.class)
public class CoreGameBoard extends GameBoard {
    public transient ArrayList<Long> recentlyKilledIDs;
    private transient Map<Long, Long> playerByFighter;

    public CoreGameBoard(int h, int w) {
        super(h, w);
        recentlyKilledIDs = new ArrayList<>();

    }

    public void setRound(int r) {
        this.round = r;
    }

    public ArrayList<Long> getRecentlyKilledIDs() {
        return recentlyKilledIDs;
    }

    public void setRecentlyKilledIDs(ArrayList<Long> recentlyKilledIDs) {
        this.recentlyKilledIDs = recentlyKilledIDs;
    }

    public Map<Long, Long> getPlayerByFighter() {
        return playerByFighter;
    }

    public void setPlayerByFighter(Map<Long, Long> playerByFighter) {
        this.playerByFighter = playerByFighter;
    }

    public void setMoneyPerTurn(Long id, int x) {
        this.playerID2moneyPerTurn.put(id, x);
    }

    public void timePassedForCurrentPlayer() {
        GameObject o = getObjectByID(getMyID());
        if (o == null) {
            playerID2money.put(getMyID(), getMoneyByID(myID) + getMoneyPerTurn(myID));
        } else {
            o.remainingReloadingTimeMM();
            o.remainingRestingTimeMM();
        }
    }

    public GameObject ShootToCell(Cell target, Integer amount) {
        GameObject o = target.getGameObject();
        if (o == null) return null;


        if (o.damageBy(amount)) {
            deleteObjectByID(o.id);
            recentlyKilledIDs.add(o.id);
            return o;
        } else {
            ResultStorage.addEvent(new AgentDamagedEvent(o.getId(), playerByFighter.get(o.getId()), getMyID(), amount, o.getHp()));
        }
        return null;
    }

    private void deleteObjectByID(Long id) {
        GameObject o = id2object.get(id);
        if (o == null) return;
        o.getCell().setGameObject(null);
        id2object.remove(id);
        id2owner.remove(id);
        for (Long pid : playerID2ids.keySet())
            playerID2ids.get(pid).remove(o);
    }

    public void setMyID(Long myID) {
        playerIDs.add(myID);
        this.myID = myID;
    }

    public void decreaseMoneyByID(Long id, int value) {
        if (value < 0) return;
        if (getMoneyByID(id) - value < 0) value = getMoneyByID(id);
        playerID2money.put(id, getMoneyByID(id) - value);
    }

    public void increasePenaltyByID(Long id, int value) {
        if (value < 0) return;
        playerID2penalty.put(id, getPenaltyByID(id) + value);
    }

    public static void giveCellToObject(Cell cell, GameObject o) {
        if (o.getCell() != null)
            o.getCell().setGameObject(null);
        cell.setGameObject(o);
        o.setCell(cell);
    }

    public void addObject(GameObject o) {
        id2object.put(o.id, o);
        id2owner.put(o.id, myID);
        //playerID2ids.get(myID).add(o.id);
    }
}
