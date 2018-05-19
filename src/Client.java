import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Client {
	private static String host;
	private static int port;

	private static Shell shell;
	private static String smessage;
	private static BufferedReader in;
	private static PrintWriter out;

	private static int startX = 0;
	private static int startY = 0;
	private static int endX = 0;
	private static int endY = 0;

	private static int option = 1;
	private static boolean change;
	private static boolean move;
	private static ArrayList<Point> points = new ArrayList();

	public static void main(String[] args) {
		host = JOptionPane.showInputDialog(null, "Server Host");
		port = Integer.parseInt(JOptionPane.showInputDialog(null, "Server Port"));
		Display display = new Display();
		shell = new Shell(display);
		Text message = new Text(shell, SWT.BORDER);
		message.setSize(160, 20);
		message.setLocation(880, 500);
		Button send = new Button(shell, SWT.PUSH);
		send.setText("Send");
		send.setLocation(1050, 500);
		send.setSize(80, 20);
		send.addSelectionListener(sendListner);
		Button findWord = new Button(shell, SWT.PUSH);
		Text text = new Text(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));
		text.setSize(400, 400);
		text.setLocation(750, 50);

		Button optPoint = new Button(shell, SWT.PUSH);
		optPoint.setText("P");
		optPoint.setLocation(10, 0);
		optPoint.setSize(40, 40);
		optPoint.addSelectionListener(optPointClick);

		Button optLine = new Button(shell, SWT.PUSH);
		optLine.setText("L");
		optLine.setLocation(50, 0);
		optLine.setSize(40, 40);
		optLine.addSelectionListener(optLineClick);

		Button optClear = new Button(shell, SWT.PUSH);
		optClear.setText("C");
		optClear.setLocation(90, 0);
		optClear.setSize(40, 40);
		optClear.addSelectionListener(optClearClick);

		Canvas canvas = new Canvas(shell, SWT.BORDER);
		canvas.setLocation(10, 50);
		canvas.setSize(700, 460);

		canvas.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				move = true;
				startX = event.x;
				startY = event.y;
			}
		});

		canvas.addListener(SWT.MouseUp, new Listener() {
			@Override
			public void handleEvent(Event event) {
				move = false;
				endX = event.x;
				endY = event.y;
				if(option == 2) {
					change = true;
					points.add(new Point(startX, startY));
					points.add(new Point(endX, endY));
					startX = 0;
					startY = 0;
					endX = 0;
					endY = 0;
				}
				points.add(new Point(0, 0));
				points.add(new Point(0, 0));
				canvas.redraw();
			}
		});

		canvas.addListener(SWT.MouseMove, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if(move) {
					if(option == 1) {
						change = true;

						points.add(new Point(event.x, event.y));
						//System.out.println(event.x + " " + event.y);
					}
				}
			}
		});


		canvas.addListener(SWT.Paint, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if(change)
				{
					GC gc = event.gc;

					gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
					gc.setAlpha(128);

					for(int i=0;i<points.size();i++) {
						if(!(points.get(i).x == 0 && points.get(i).y == 0)) {
							if(i!=0 && i!= (points.size()-1) && i!= (points.size()-2) && !(points.get(i-1).x == 0 && points.get(i-1).y == 0)) {
								gc.drawLine(points.get(i).x, points.get(i).y, points.get(i-1).x, points.get(i-1).y);
							}
							else {
								gc.drawPoint(points.get(i).x, points.get(i).y);
							}
						}
					}
				}
			}
		});


		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())

				display.sleep();
		}
		display.dispose();
	}

	static ModifyListener listenerH = new ModifyListener() {
		/** {@inheritDoc} */
		public void modifyText(ModifyEvent e) {
			smessage = ((Text) e.getSource()).getText();
		}
	};

	static SelectionAdapter optPointClick = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			option = 1;
		}
	};

	static SelectionAdapter optLineClick = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			option = 2;
		}
	};

	static SelectionAdapter optClearClick = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			points.clear();
			change = true;
		}
	};

	static SelectionAdapter sendListner = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			try {
				Socket socket = new Socket(host, port);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
                String message_distant = in.readLine();
                System.out.println(message_distant);
			
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			smessage = null;
		}
	};
}
