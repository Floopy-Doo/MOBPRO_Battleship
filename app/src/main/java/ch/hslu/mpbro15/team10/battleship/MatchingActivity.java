package ch.hslu.mpbro15.team10.battleship;
//
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.games.Games;
//import com.google.android.gms.games.GamesActivityResultCodes;
//import com.google.android.gms.games.multiplayer.Invitation;
//import com.google.android.gms.games.multiplayer.Multiplayer;
//import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
//import com.google.android.gms.games.multiplayer.Participant;
//import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
//import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
//import com.google.android.gms.games.multiplayer.realtime.Room;
//import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
//import com.google.android.gms.plus.Plus;
//
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
import ch.hslu.mpbro15.team10.battleship.basegame.BaseMultiplayerAcitvity;
//import ch.hslu.mpbro15.team10.battleship.GooglePlayBaseGame.BaseGameUtils;
//
///**
// * Created by dave on 03.05.2015.
// */
public class MatchingActivity extends BaseMultiplayerAcitvity {
//        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
//        RealTimeMessageReceivedListener,
//        OnInvitationReceivedListener {
//
//    // Request codes for the UIs that we show with startActivityForResult:
//    final static int RC_SELECT_PLAYERS = 10000;
//    final static int RC_INVITATION_INBOX = 10001;
//    final static int RC_WAITING_ROOM = 10002;
//
//    // Request code used to invoke sign in user interactions.
//    private static final int RC_SIGN_IN = 9001;
//
//    // Are we currently resolving a connection failure?
//    private boolean mResolvingConnectionFailure = false;
//
//    // Has the user clicked the sign-in button?
//    private boolean mSignInClicked = false;
//
//    // Set to true to automatically start the sign in flow when the Activity starts.
//    // Set to false to require the user to click the button in order to sign in.
//    private boolean mAutoStartSignInFlow = true;
//
//    // Room ID where the currently active game is taking place; null if we're
//    // not playing.
//    String mRoomId = null;
//
//    // Are we playing in multiplayer mode?
//    boolean mMultiplayer = false;
//
//    // The participants in the currently active game
//    ArrayList<Participant> mParticipants = null;
//
//    // My participant ID in the currently active game
//    String mMyId = null;
//
//    // If non-null, this is the id of the invitation we received via the
//    // invitation listener
//    String mIncomingInvitationId = null;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.matching);
//
//        // Create the Google Api Client with access to Plus and Games
//        GOOGLE_PLAY_API_CLIENT = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
//                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
//                .build();
//
//        registerClickEvents();
//    }
//
//    private void registerClickEvents() {
//        // Add Click events to buttons
//        ((Button)findViewById(R.id.button_sign_in))
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        buttonSignInClick();
//                    }
//                });
//        ((Button)findViewById(R.id.button_sign_out))
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        buttonSignInClick();
//                    }
//                });
//        ((Button)findViewById(R.id.button_invite_players))
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        buttonSignInClick();
//                    }
//                });
//        ((Button)findViewById(R.id.button_see_invitations))
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        buttonSignInClick();
//                    }
//                });
//        ((Button)findViewById(R.id.button_accept_popup_invitation))
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        buttonSignInClick();
//                    }
//                });
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int responseCode,
//                                 Intent intent) {
//        super.onActivityResult(requestCode, responseCode, intent);
//
//        switch (requestCode) {
//            case RC_SELECT_PLAYERS:
//                // we got the result from the "select players" UI -- ready to create the room
//                handleSelectPlayersResult(responseCode, intent);
//                break;
//            case RC_INVITATION_INBOX:
//                // we got the result from the "select invitation" UI (invitation inbox). We're
//                // ready to accept the selected invitation:
//                handleInvitationInboxResult(responseCode, intent);
//                break;
//            case RC_WAITING_ROOM:
//                // we got the result from the "waiting room" UI.
//                if (responseCode == Activity.RESULT_OK) {
//                    // ready to start playing
//                    Log.d(TAG, "Starting game (waiting room returned OK).");
//                    startGame(true);
//                } else if (responseCode == GamesActivityResultCodes.RESULT_LEFT_ROOM) {
//                    // player indicated that they want to leave the room
//                    leaveRoom();
//                } else if (responseCode == Activity.RESULT_CANCELED) {
//                    // Dialog was cancelled (user pressed back key, for instance). In our game,
//                    // this means leaving the room too. In more elaborate games, this could mean
//                    // something else (like minimizing the waiting room UI).
//                    leaveRoom();
//                }
//                break;
//            case RC_SIGN_IN:
//                Log.d(TAG, "onActivityResult with requestCode == RC_SIGN_IN, responseCode="
//                        + responseCode + ", intent=" + intent);
//                mSignInClicked = false;
//                mResolvingConnectionFailure = false;
//                if (responseCode == RESULT_OK && GOOGLE_PLAY_API_CLIENT != null) {
//                    GOOGLE_PLAY_API_CLIENT.connect();
//                } else {
//                    BaseGameUtils.showActivityResultError(this, requestCode, responseCode, R.string.signin_other_error);
//                }
//                break;
//        }
//        super.onActivityResult(requestCode, responseCode, intent);
//    }
//
//    private void buttonAcceptPopupInviteClick() {
//        // user wants to accept the invitation shown on the invitation popup
//        // (the one we got through the OnInvitationReceivedListener).
//        acceptInviteToRoom(mIncomingInvitationId);
//        mIncomingInvitationId = null;
//    }
//
//    private void buttonSeeInvitationsClick() {
//        Intent intent;// show list of pending invitations
//        intent = Games.Invitations.getInvitationInboxIntent(mGoogleApiClient);
//        switchToScreen(R.id.screen_wait);
//        startActivityForResult(intent, RC_INVITATION_INBOX);
//    }
//
//    private void buttonInvitePlayerClick() {
//        Intent intent;// show list of invitable players
//        intent = Games.RealTimeMultiplayer.getSelectOpponentsIntent(mGoogleApiClient, 1, 1);
//        switchToScreen(R.id.screen_wait);
//        startActivityForResult(intent, RC_SELECT_PLAYERS);
//    }
//
//    private void buttonSignOutClick() {
//        // user wants to sign out
//        // sign out.
//        Log.d(TAG, "Sign-out button clicked");
//        mSignInClicked = false;
//        Games.signOut(mGoogleApiClient);
//        mGoogleApiClient.disconnect();
//        switchToScreen(R.id.screen_sign_in);
//    }
//
//    private void buttonSignInClick() {
//        // start the sign-in flow
//        Log.d(TAG, "Sign-in button clicked");
//        mSignInClicked = true;
//        GOOGLE_PLAY_API_CLIENT.connect();
//    }
//
//    // Handle the result of the "Select players UI" we launched when the user clicked the
//    // "Invite friends" button. We react by creating a room with those players.
//    private void handleSelectPlayersResult(int response, Intent data) {
//        if (response != Activity.RESULT_OK) {
//            Log.w(TAG, "*** select players UI cancelled, " + response);
//            switchToMainScreen();
//            return;
//        }
//
//        Log.d(TAG, "Select players UI succeeded.");
//
//        // get the invitee list
//        final ArrayList<String> invitees = data.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);
//        Log.d(TAG, "Invitee count: " + invitees.size());
//
//        // get the automatch criteria
//        Bundle autoMatchCriteria = null;
//        int minAutoMatchPlayers = data.getIntExtra(Multiplayer.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
//        int maxAutoMatchPlayers = data.getIntExtra(Multiplayer.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);
//        if (minAutoMatchPlayers > 0 || maxAutoMatchPlayers > 0) {
//            autoMatchCriteria = RoomConfig.createAutoMatchCriteria(
//                    minAutoMatchPlayers, maxAutoMatchPlayers, 0);
//            Log.d(TAG, "Automatch criteria: " + autoMatchCriteria);
//        }
//
//        // create the room
//        Log.d(TAG, "Creating room...");
//        RoomConfig.Builder rtmConfigBuilder = RoomConfig.builder(this);
//        rtmConfigBuilder.addPlayersToInvite(invitees);
//        rtmConfigBuilder.setMessageReceivedListener(this);
//        rtmConfigBuilder.setRoomStatusUpdateListener(this);
//        if (autoMatchCriteria != null) {
//            rtmConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);
//        }
//        switchToScreen(R.id.screen_wait);
//        keepScreenOn();
//        resetGameVars();
//        Games.RealTimeMultiplayer.create(mGoogleApiClient, rtmConfigBuilder.build());
//        Log.d(TAG, "Room created, waiting for it to be ready...");
//    }
//
//    // Handle the result of the invitation inbox UI, where the player can pick an invitation
//    // to accept. We react by accepting the selected invitation, if any.
//    private void handleInvitationInboxResult(int response, Intent data) {
//        if (response != Activity.RESULT_OK) {
//            Log.w(TAG, "*** invitation inbox UI cancelled, " + response);
//            switchToMainScreen();
//            return;
//        }
//
//        Log.d(TAG, "Invitation inbox UI succeeded.");
//        Invitation inv = data.getExtras().getParcelable(Multiplayer.EXTRA_INVITATION);
//
//        // accept invitation
//        acceptInviteToRoom(inv.getInvitationId());
//    }
//
//    // Accept the given invitation.
//    void acceptInviteToRoom(String invId) {
//        // accept the invitation
//        Log.d(TAG, "Accepting invitation: " + invId);
//        RoomConfig.Builder roomConfigBuilder = RoomConfig.builder(this);
//        roomConfigBuilder.setInvitationIdToAccept(invId)
//                .setMessageReceivedListener(this)
//                .setRoomStatusUpdateListener(this);
//        switchToScreen(R.id.screen_wait);
//        keepScreenOn();
//        resetGameVars();
//        Games.RealTimeMultiplayer.join(mGoogleApiClient, roomConfigBuilder.build());
//    }
//
//    // Activity is going to the background. We have to leave the current room.
//    @Override
//    public void onStop() {
//        Log.d(TAG, "**** got onStop");
//
//        // if we're in a room, leave it.
//        leaveRoom();
//
//        // stop trying to keep the screen on
//        stopKeepingScreenOn();
//
//        if (mGoogleApiClient == null || !mGoogleApiClient.isConnected()) {
//            switchToScreen(R.id.screen_sign_in);
//        } else {
//            switchToScreen(R.id.screen_wait);
//        }
//        super.onStop();
//    }
//
//    // Activity just got to the foreground. We switch to the wait screen because we will now
//    // go through the sign-in flow (remember that, yes, every time the Activity comes back to the
//    // foreground we go through the sign-in flow -- but if the user is already authenticated,
//    // this flow simply succeeds and is imperceptible).
//    @Override
//    public void onStart() {
//        switchToScreen(R.id.screen_wait);
//        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
//            Log.w(TAG,
//                    "GameHelper: client was already connected on onStart()");
//        } else {
//            Log.d(TAG, "Connecting client.");
//            mGoogleApiClient.connect();
//        }
//        super.onStart();
//    }
//
//    // Handle back key to make sure we cleanly leave a game if we are in the middle of one
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent e) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && mCurScreen == R.id.screen_game) {
//            leaveRoom();
//            return true;
//        }
//        return super.onKeyDown(keyCode, e);
//    }
//
//    // Leave the room.
//    void leaveRoom() {
//        Log.d(TAG, "Leaving room.");
//        mSecondsLeft = 0;
//        stopKeepingScreenOn();
//        if (mRoomId != null) {
//            Games.RealTimeMultiplayer.leave(mGoogleApiClient, this, mRoomId);
//            mRoomId = null;
//            switchToScreen(R.id.screen_wait);
//        } else {
//            switchToMainScreen();
//        }
//    }
//
//    // Show the waiting room UI to track the progress of other players as they enter the
//    // room and get connected.
//    void showWaitingRoom(Room room) {
//        // minimum number of players required for our game
//        // For simplicity, we require everyone to join the game before we start it
//        // (this is signaled by Integer.MAX_VALUE).
//        final int MIN_PLAYERS = Integer.MAX_VALUE;
//        Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(mGoogleApiClient, room, MIN_PLAYERS);
//
//        // show waiting room UI
//        startActivityForResult(i, RC_WAITING_ROOM);
//    }
//
//    /*
//     * CALLBACKS SECTION. This section shows how we implement the several games
//     * API callbacks.
//     */
//
//    @Override
//    public void onConnected(Bundle connectionHint) {
//        Log.d(TAG, "onConnected() called. Sign in successful!");
//
//        Log.d(TAG, "Sign-in succeeded.");
//
//        // register listener so we are notified if we receive an invitation to play
//        // while we are in the game
//        Games.Invitations.registerInvitationListener(mGoogleApiClient, this);
//
//        if (connectionHint != null) {
//            Log.d(TAG, "onConnected: connection hint provided. Checking for invite.");
//            Invitation inv = connectionHint
//                    .getParcelable(Multiplayer.EXTRA_INVITATION);
//            if (inv != null && inv.getInvitationId() != null) {
//                // retrieve and cache the invitation ID
//                Log.d(TAG, "onConnected: connection hint has a room invite!");
//                acceptInviteToRoom(inv.getInvitationId());
//                return;
//            }
//        }
//        switchToMainScreen();
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//        Log.d(TAG, "onConnectionSuspended() called. Trying to reconnect.");
//        mGoogleApiClient.connect();
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//        Log.d(TAG, "onConnectionFailed() called, result: " + connectionResult);
//
//        if (mResolvingConnectionFailure) {
//            Log.d(TAG, "onConnectionFailed() ignoring connection failure; already resolving.");
//            return;
//        }
//
//        if (mSignInClicked || mAutoStartSignInFlow) {
//            mAutoStartSignInFlow = false;
//            mSignInClicked = false;
//            mResolvingConnectionFailure = BaseGameUtils.resolveConnectionFailure(this, mGoogleApiClient,
//                    connectionResult, RC_SIGN_IN, getString(R.string.signin_other_error));
//        }
//
//        switchToScreen(R.id.screen_sign_in);
//    }
//
//
//
//
//    // Show error message about game being cancelled and return to main screen.
//    void showGameError() {
//        BaseGameUtils.makeSimpleDialog(this, getString(R.string.game_problem));
//        switchToMainScreen();
//    }
//
//
//
//
//    void updateRoom(Room room) {
//        if (room != null) {
//            mParticipants = room.getParticipants();
//        }
//        if (mParticipants != null) {
//            updatePeerScoresDisplay();
//        }
//    }
//
//    /*
//     * GAME LOGIC SECTION. Methods that implement the game's rules.
//     */
//
//    // Current state of the game:
//    int mSecondsLeft = -1; // how long until the game ends (seconds)
//    final static int GAME_DURATION = 20; // game duration, seconds.
//    int mScore = 0; // user's current score
//
//    // Reset game variables in preparation for a new game.
//    void resetGameVars() {
//        mSecondsLeft = GAME_DURATION;
//        mScore = 0;
//        mParticipantScore.clear();
//        mFinishedParticipants.clear();
//    }
//
//    // Start the gameplay phase of the game.
//    void startGame(boolean multiplayer) {
//        mMultiplayer = multiplayer;
//        updateScoreDisplay();
//        broadcastScore(false);
//        switchToScreen(R.id.screen_game);
//
//        findViewById(R.id.button_click_me).setVisibility(View.VISIBLE);
//
//        // run the gameTick() method every second to update the game.
//        final Handler h = new Handler();
//        h.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (mSecondsLeft <= 0)
//                    return;
//                gameTick();
//                h.postDelayed(this, 1000);
//            }
//        }, 1000);
//    }
//
//    // Game tick -- update countdown, check if game ended.
//    void gameTick() {
//        if (mSecondsLeft > 0)
//            --mSecondsLeft;
//
//        // update countdown
//        ((TextView) findViewById(R.id.countdown)).setText("0:" +
//                (mSecondsLeft < 10 ? "0" : "") + String.valueOf(mSecondsLeft));
//
//        if (mSecondsLeft <= 0) {
//            // finish game
//            findViewById(R.id.button_click_me).setVisibility(View.GONE);
//            broadcastScore(true);
//        }
//    }
//
//    // indicates the player scored one point
//    void scoreOnePoint() {
//        if (mSecondsLeft <= 0)
//            return; // too late!
//        ++mScore;
//        updateScoreDisplay();
//        updatePeerScoresDisplay();
//
//        // broadcast our new score to our peers
//        broadcastScore(false);
//    }
//
//    /*
//     * COMMUNICATIONS SECTION. Methods that implement the game's network
//     * protocol.
//     */
//
//    // Score of other participants. We update this as we receive their scores
//    // from the network.
//    Map<String, Integer> mParticipantScore = new HashMap<>();
//
//    // Participants who sent us their final score.
//    Set<String> mFinishedParticipants = new HashSet<>();
//
//    // Called when we receive a real-time message from the network.
//    // Messages in our game are made up of 2 bytes: the first one is 'F' or 'U'
//    // indicating
//    // whether it's a final or interim score. The second byte is the score.
//    // There is also the
//    // 'S' message, which indicates that the game should start.
//    @Override
//    public void onRealTimeMessageReceived(RealTimeMessage rtm) {
//        byte[] buf = rtm.getMessageData();
//        String sender = rtm.getSenderParticipantId();
//        Log.d(TAG, "Message received: " + (char) buf[0] + "/" + (int) buf[1]);
//
//        if (buf[0] == 'F' || buf[0] == 'U') {
//            // score update.
//            int existingScore = mParticipantScore.containsKey(sender) ?
//                    mParticipantScore.get(sender) : 0;
//
//            int thisScore = (int) buf[1];
//            if (thisScore > existingScore) {
//                // this check is necessary because packets may arrive out of
//                // order, so we
//                // should only ever consider the highest score we received, as
//                // we know in our
//                // game there is no way to lose points. If there was a way to
//                // lose points,
//                // we'd have to add a "serial number" to the packet.
//                mParticipantScore.put(sender, thisScore);
//            }
//
//            // update the scores on the screen
//            updatePeerScoresDisplay();
//
//            // if it's a final score, mark this participant as having finished
//            // the game
//            if ((char) buf[0] == 'F') {
//                mFinishedParticipants.add(rtm.getSenderParticipantId());
//            }
//        }
//    }
//
//    // Broadcast my score to everybody else.
//    void broadcastScore(boolean finalScore) {
//        if (!mMultiplayer)
//            return; // playing single-player mode
//
//        // First byte in message indicates whether it's a final score or not
//        mMsgBuf[0] = (byte) (finalScore ? 'F' : 'U');
//
//        // Second byte is the score.
//        mMsgBuf[1] = (byte) mScore;
//
//        // Send to every other participant.
//        for (Participant p : mParticipants) {
//            if (p.getParticipantId().equals(mMyId))
//                continue;
//            if (p.getStatus() != Participant.STATUS_JOINED)
//                continue;
//            if (finalScore) {
//                // final score notification must be sent via reliable message
//                Games.RealTimeMultiplayer.sendReliableMessage(mGoogleApiClient, null, mMsgBuf,
//                        mRoomId, p.getParticipantId());
//            } else {
//                // it's an interim score notification, so we can use unreliable
//                Games.RealTimeMultiplayer.sendUnreliableMessage(mGoogleApiClient, mMsgBuf, mRoomId,
//                        p.getParticipantId());
//            }
//        }
//    }
//
//    /*
//     * UI SECTION. Methods that implement the game's UI.
//     */
//
//    // This array lists everything that's clickable, so we can install click
//    // event handlers.
//    final static int[] CLICKABLES = {
//            R.id.button_accept_popup_invitation,
//            R.id.button_invite_players,
//            R.id.button_see_invitations,
//            R.id.button_sign_in,
//            R.id.button_sign_out,
//            R.id.button_click_me
//    };
//
//    // This array lists all the individual screens our game has.
//    final static int[] SCREENS = {
//            R.id.screen_game, R.id.screen_main, R.id.screen_sign_in,
//            R.id.screen_wait
//    };
//    int mCurScreen = -1;
//
//    void switchToScreen(int screenId) {
//        // make the requested screen visible; hide all others.
//        for (int id : SCREENS) {
//            findViewById(id).setVisibility(screenId == id ? View.VISIBLE : View.GONE);
//        }
//        mCurScreen = screenId;
//
//        // should we show the invitation popup?
//        boolean showInvPopup;
//        if (mIncomingInvitationId == null) {
//            // no invitation, so no popup
//            showInvPopup = false;
//        } else if (mMultiplayer) {
//            // if in multiplayer, only show invitation on main screen
//            showInvPopup = (mCurScreen == R.id.screen_main);
//        } else {
//            // single-player: show on main screen and gameplay screen
//            showInvPopup = (mCurScreen == R.id.screen_main || mCurScreen == R.id.screen_game);
//        }
//        findViewById(R.id.invitation_popup).setVisibility(showInvPopup ? View.VISIBLE : View.GONE);
//    }
//
//    void switchToMainScreen() {
//        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
//            switchToScreen(R.id.screen_main);
//        } else {
//            switchToScreen(R.id.screen_sign_in);
//        }
//    }
//
//    // updates the label that shows my score
//    void updateScoreDisplay() {
//        ((TextView) findViewById(R.id.my_score)).setText(formatScore(mScore));
//    }
//
//    // formats a score as a three-digit number
//    String formatScore(int i) {
//        if (i < 0)
//            i = 0;
//        String s = String.valueOf(i);
//        return s.length() == 1 ? "00" + s : s.length() == 2 ? "0" + s : s;
//    }
//
//    // updates the screen with the scores from our peers
//    void updatePeerScoresDisplay() {
//        ((TextView) findViewById(R.id.score0)).setText(formatScore(mScore) + " - Me");
//        int[] arr = {
//                R.id.score1, R.id.score2, R.id.score3
//        };
//        int i = 0;
//
//        if (mRoomId != null) {
//            for (Participant p : mParticipants) {
//                String pid = p.getParticipantId();
//                if (pid.equals(mMyId))
//                    continue;
//                if (p.getStatus() != Participant.STATUS_JOINED)
//                    continue;
//                int score = mParticipantScore.containsKey(pid) ? mParticipantScore.get(pid) : 0;
//                ((TextView) findViewById(arr[i])).setText(formatScore(score) + " - " +
//                        p.getDisplayName());
//                ++i;
//            }
//        }
//
//        for (; i < arr.length; ++i) {
//            ((TextView) findViewById(arr[i])).setText("");
//        }
//    }
//
//    /*
//     * MISC SECTION. Miscellaneous methods.
//     */
//
//
//
}
