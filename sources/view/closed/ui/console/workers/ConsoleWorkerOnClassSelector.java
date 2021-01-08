package view.closed.ui.console.workers;

import model.open.Requests;
import view.closed.ui.console.ConsoleWorker;
import view.closed.ui.console.utils.ConsoleTemplate;
import view.open.Context;

public class		ConsoleWorkerOnClassSelector extends ConsoleWorker
{
	@Override
	public void		execute(Requests.Abstract request)
	{
		clean();
		write(getText());
		promptInput(Context.parse(request));
	}

	private String		getText()
	{
		ConsoleTemplate	template;

		template = new ConsoleTemplate("view/console/Template-ClassSelector.txt");
		template.put("TITLE", "Choose class : ", ConsoleTemplate.Style.BOLD);
		template.put("COMMANDS", "Commands : ", ConsoleTemplate.Style.BOLD);

		return template.toString();
	}
}
