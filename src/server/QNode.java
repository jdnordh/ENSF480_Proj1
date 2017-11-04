package server;

public class QNode<D> {
	QNode<D> next;
	D data;
	
	/** Copy constructor for Data */
	public QNode(D d){
		data = d;
		next = null;
	}
	
	/** Copy constructor for Node*/
	public QNode(QNode<D> n){
		data = n.getData();
		next = null;
	}
	
	/** get the data */
	public D getData(){
		return data;
	}
	
	/** Set the data */
	public void setData(D d){
		data = d;
	}
	
	/** get the next node */
	public QNode<D> next(){
		return next;
	}
	
	/** set the next node */
	public void setNext(QNode<D> n){
		next = n;
	}
}
