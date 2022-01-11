

package com.mysql.cj.jdbc.exceptions;

import java.sql.SQLException;

import com.mysql.cj.exceptions.CJCommunicationsException;
import com.mysql.cj.exceptions.CJConnectionFeatureNotAvailableException;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.exceptions.CJOperationNotSupportedException;
import com.mysql.cj.exceptions.CJPacketTooBigException;
import com.mysql.cj.exceptions.CJTimeoutException;
import com.mysql.cj.exceptions.ConnectionIsClosedException;
import com.mysql.cj.exceptions.DataConversionException;
import com.mysql.cj.exceptions.DataReadException;
import com.mysql.cj.exceptions.DataTruncationException;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.exceptions.InvalidConnectionAttributeException;
import com.mysql.cj.exceptions.MysqlErrorNumbers;
import com.mysql.cj.exceptions.NumberOutOfRange;
import com.mysql.cj.exceptions.OperationCancelledException;
import com.mysql.cj.exceptions.SSLParamsException;
import com.mysql.cj.exceptions.StatementIsClosedException;
import com.mysql.cj.exceptions.UnableToConnectException;
import com.mysql.cj.exceptions.WrongArgumentException;

public class SQLExceptionsMapping {

    public static SQLException translateException(Throwable ex, ExceptionInterceptor interceptor) {
        if (ex instanceof SQLException) {
            return (SQLException) ex;

        } else if (ex.getCause() != null && ex.getCause() instanceof SQLException) {
            return (SQLException) ex.getCause();

        } else if (ex instanceof CJCommunicationsException) {
            return SQLError.createCommunicationsException(ex.getMessage(), ex, interceptor);

        } else if (ex instanceof CJConnectionFeatureNotAvailableException) {
            return new ConnectionFeatureNotAvailableException(ex.getMessage(), ex);

        } else if (ex instanceof SSLParamsException) {
            return SQLError.createSQLException(ex.getMessage(), MysqlErrorNumbers.SQL_STATE_BAD_SSL_PARAMS, 0, false, ex, interceptor);

        } else if (ex instanceof ConnectionIsClosedException) {
            return SQLError.createSQLException(ex.getMessage(), MysqlErrorNumbers.SQL_STATE_CONNECTION_NOT_OPEN, ex, interceptor);

        } else if (ex instanceof InvalidConnectionAttributeException) {
            return SQLError.createSQLException(ex.getMessage(), MysqlErrorNumbers.SQL_STATE_INVALID_CONNECTION_ATTRIBUTE, ex, interceptor);

        } else if (ex instanceof UnableToConnectException) {
            return SQLError.createSQLException(ex.getMessage(), MysqlErrorNumbers.SQL_STATE_UNABLE_TO_CONNECT_TO_DATASOURCE, ex, interceptor);

        } else if (ex instanceof StatementIsClosedException) {
            return SQLError.createSQLException(ex.getMessage(), MysqlErrorNumbers.SQL_STATE_ILLEGAL_ARGUMENT, ex, interceptor);

        } else if (ex instanceof WrongArgumentException) {
            return SQLError.createSQLException(ex.getMessage(), MysqlErrorNumbers.SQL_STATE_ILLEGAL_ARGUMENT, ex, interceptor);

        } else if (ex instanceof StringIndexOutOfBoundsException) {
            return SQLError.createSQLException(ex.getMessage(), MysqlErrorNumbers.SQL_STATE_ILLEGAL_ARGUMENT, ex, interceptor);

        } else if (ex instanceof NumberOutOfRange) {
            // must come before DataReadException as it's more specific
            return SQLError.createSQLException(ex.getMessage(), MysqlErrorNumbers.SQL_STATE_NUMERIC_VALUE_OUT_OF_RANGE, ex, interceptor);

        } else if (ex instanceof DataConversionException) {
            // must come before DataReadException as it's more specific
            return SQLError.createSQLException(ex.getMessage(), MysqlErrorNumbers.SQL_STATE_INVALID_CHARACTER_VALUE_FOR_CAST, ex, interceptor);

        } else if (ex instanceof DataReadException) {
            return SQLError.createSQLException(ex.getMessage(), MysqlErrorNumbers.SQL_STATE_ILLEGAL_ARGUMENT, ex, interceptor);

        } else if (ex instanceof DataTruncationException) {
            return new MysqlDataTruncation(((DataTruncationException) ex).getMessage(), ((DataTruncationException) ex).getIndex(),
                    ((DataTruncationException) ex).isParameter(), ((DataTruncationException) ex).isRead(), ((DataTruncationException) ex).getDataSize(),
                    ((DataTruncationException) ex).getTransferSize(), ((DataTruncationException) ex).getVendorCode());

        } else if (ex instanceof CJPacketTooBigException) {
            return new PacketTooBigException(ex.getMessage());

        } else if (ex instanceof OperationCancelledException) {
            return new MySQLStatementCancelledException(ex.getMessage());

        } else if (ex instanceof CJTimeoutException) {
            return new MySQLTimeoutException(ex.getMessage());

        } else if (ex instanceof CJOperationNotSupportedException) {
            return new OperationNotSupportedException(ex.getMessage());

        } else if (ex instanceof UnsupportedOperationException) {
            return new OperationNotSupportedException(ex.getMessage());

        } else if (ex instanceof CJException) {
            return SQLError.createSQLException(ex.getMessage(), ((CJException) ex).getSQLState(), ((CJException) ex).getVendorCode(),
                    ((CJException) ex).isTransient(), ex.getCause(), interceptor);

        } else {
            return SQLError.createSQLException(ex.getMessage(), MysqlErrorNumbers.SQL_STATE_GENERAL_ERROR, ex, interceptor);
        }
    }

    public static SQLException translateException(Throwable ex) {
        return translateException(ex, null);
    }
}
