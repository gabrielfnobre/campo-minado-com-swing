package br.com.nobrecoder.cm.model;

@FunctionalInterface
public interface ObserverField {
	public void eventOccured(Field field, FieldEvent event);
}
