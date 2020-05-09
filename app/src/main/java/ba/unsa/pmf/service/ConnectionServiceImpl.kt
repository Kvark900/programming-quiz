package ba.unsa.pmf.service

import android.telecom.Connection
import android.telecom.ConnectionRequest
import android.telecom.ConnectionService
import android.telecom.PhoneAccountHandle

class ConnectionServiceImpl: ConnectionService() {


    override fun onCreateOutgoingConnection(connectionManagerPhoneAccount: PhoneAccountHandle?,
                                            request: ConnectionRequest?): Connection {
        return super.onCreateOutgoingConnection(connectionManagerPhoneAccount, request)
    }
}
