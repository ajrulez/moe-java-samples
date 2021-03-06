// Copyright (c) 2015, Intel Corporation
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are
// met:
//
// 1. Redistributions of source code must retain the above copyright
// notice, this list of conditions and the following disclaimer.
// 2. Redistributions in binary form must reproduce the above
// copyright notice, this list of conditions and the following disclaimer
// in the documentation and/or other materials provided with the
// distribution.
// 3. Neither the name of the copyright holder nor the names of its
// contributors may be used to endorse or promote products derived from
// this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
// LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
// A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
// HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
// SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
// LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
// THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package com.intel.moe.samples.rssreader.ios;

import com.intel.moe.natj.general.Pointer;

import java.util.ArrayList;

import ios.foundation.NSIndexPath;
import ios.uikit.UITableView;
import ios.uikit.UITableViewCell;
import ios.uikit.UITableViewController;

public abstract class CustomCellTableController extends UITableViewController {

	public static final String CELL_IDENTIFIER = "Cell";

	private final ArrayList<Object> data = new ArrayList<Object>();

	public static interface EventListener {
		public void tableViewDidSelectRow(UITableView tableView, Object row);
	}

	private EventListener listener;

	public static native CustomCellTableController alloc();

	@Override
	public native CustomCellTableController init();

	protected CustomCellTableController(Pointer peer) {
		super(peer);
	}

	@Override
	public void viewDidLoad() {
		super.viewDidLoad();

		prepareController();
	}

	protected abstract void prepareController();

	protected void add(Object elem) {
		data.add(elem);
	}

	@Override
	public long numberOfSectionsInTableView(UITableView tableView) {
		return 1;
	}

	@Override
	public long tableViewNumberOfRowsInSection(UITableView tableView, long section) {
		return data.size();
	}

	@Override
	public UITableViewCell tableViewCellForRowAtIndexPath(
			UITableView tableView, NSIndexPath indexPath) {
		UITableViewCell cell = (UITableViewCell) tableView
				.dequeueReusableCellWithIdentifierForIndexPath(CELL_IDENTIFIER,
						indexPath);

		setupCellAtIndex(cell, data.get((int)indexPath.row()));

		return cell;
	}

	protected abstract void setupCellAtIndex(UITableViewCell cell,
			Object rowData);

	@Override
	public void tableViewDidSelectRowAtIndexPath(UITableView tableView,
			NSIndexPath indexPath) {
		if (listener != null) {
			listener.tableViewDidSelectRow(tableView, data.get((int)indexPath.row()));
		}
	}

	public ArrayList<Object> getOptions() {
		return data;
	}

	public EventListener getListener() {
		return listener;
	}

	public void setListener(EventListener listener) {
		this.listener = listener;
	}

}
