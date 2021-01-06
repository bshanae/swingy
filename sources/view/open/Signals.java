package view.open;

import lombok.Getter;

public abstract class					Signals
{
	public interface					Abstract {}

	public static class					Null implements Abstract {}

// -----------------------------------> Console

	public static class					Console implements Abstract
	{
		@Getter
		private final Context			context;

		@Getter
		private final String			input;

		public							Console(Context context, String input)
		{
			this.context = context;
			this.input = input;
		}
	}

// -----------------------------------> GUI

	public static class					Gui implements Abstract
	{
		@Getter
		private final ButtonId			buttonId;

		@Getter
		private final String			string;

		public 							Gui(ButtonId buttonId)
		{
			this.buttonId = buttonId;
			this.string = null;
		}

		public 							Gui(ButtonId buttonId, String string)
		{
			this.buttonId = buttonId;
			this.string = string;
		}
	}
}
