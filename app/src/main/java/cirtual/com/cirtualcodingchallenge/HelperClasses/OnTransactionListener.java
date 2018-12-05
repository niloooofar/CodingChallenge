package cirtual.com.cirtualcodingchallenge.HelperClasses;

public interface OnTransactionListener<TResult> {
    void onSuccess(TResult var1);
    void onFailure();
}
