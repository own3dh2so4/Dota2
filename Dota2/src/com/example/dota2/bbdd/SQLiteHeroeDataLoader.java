package com.example.dota2.bbdd;

import java.util.List;

import android.content.Context;

import com.example.dota2.AbstractDataLoader;
import com.example.dota2.ContentChangingTask;
import com.example.dota2.bbdd.auxiliar.QueryCondition;
import com.example.dota2.modelo.Heroe;


public class SQLiteHeroeDataLoader extends AbstractDataLoader<List<Heroe>> {
		private BBDDHeroe mDataSource;
		private QueryCondition[] conditios;
		

		public SQLiteHeroeDataLoader(Context context, BBDDHeroe dataSource, QueryCondition[] cond) {
			super(context);
			mDataSource = dataSource;
			conditios=cond;
		}

		@Override
		protected List<Heroe> buildList() {
			List<Heroe> testList = mDataSource.findByCondition(conditios);
			return testList;
		}

		public void insert(Heroe entity) {
			new InsertTask(this).execute(entity);
		}

		public void update(Heroe entity) {
			new UpdateTask(this).execute(entity);
		}

		public void delete(Heroe entity) {
			new DeleteTask(this).execute(entity);
		}

		private class InsertTask extends ContentChangingTask<Heroe, Void, Void> {
			InsertTask(SQLiteHeroeDataLoader loader) {
				super(loader);
			}

			@Override
			protected Void doInBackground(Heroe... params) {
				mDataSource.insert(params[0]);
				return (null);
			}
		}

		private class UpdateTask extends ContentChangingTask<Heroe, Void, Void> {
			UpdateTask(SQLiteHeroeDataLoader loader) {
				super(loader);
			}

			@Override
			protected Void doInBackground(Heroe... params) {
				mDataSource.update(params[0]);
				return (null);
			}
		}

		private class DeleteTask extends ContentChangingTask<Heroe, Void, Void> {
			DeleteTask(SQLiteHeroeDataLoader loader) {
				super(loader);
			}

			@Override
			protected Void doInBackground(Heroe... params) {
				//mDataSource.delete(params[0]);
				return (null);
			}
		}
	}

