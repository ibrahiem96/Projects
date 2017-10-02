namespace ChicagoCrime
{
  partial class Form1
  {
    /// <summary>
    /// Required designer variable.
    /// </summary>
    private System.ComponentModel.IContainer components = null;

    /// <summary>
    /// Clean up any resources being used.
    /// </summary>
    /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
    protected override void Dispose(bool disposing)
    {
      if (disposing && (components != null))
      {
        components.Dispose();
      }
      base.Dispose(disposing);
    }

    #region Windows Form Designer generated code

    /// <summary>
    /// Required method for Designer support - do not modify
    /// the contents of this method with the code editor.
    /// </summary>
    private void InitializeComponent()
    {
      this.cmdByYear = new System.Windows.Forms.Button();
      this.graphPanel = new System.Windows.Forms.Panel();
      this.txtFilename = new System.Windows.Forms.TextBox();
      this.button1 = new System.Windows.Forms.Button();
      this.button2 = new System.Windows.Forms.Button();
      this.button3 = new System.Windows.Forms.Button();
      this.textBox1 = new System.Windows.Forms.TextBox();
      this.textBox2 = new System.Windows.Forms.TextBox();
      this.button4 = new System.Windows.Forms.Button();
      this.SuspendLayout();
      // 
      // cmdByYear
      // 
      this.cmdByYear.Location = new System.Drawing.Point(12, 12);
      this.cmdByYear.Name = "cmdByYear";
      this.cmdByYear.Size = new System.Drawing.Size(95, 62);
      this.cmdByYear.TabIndex = 0;
      this.cmdByYear.Text = "By Year";
      this.cmdByYear.UseVisualStyleBackColor = true;
      this.cmdByYear.Click += new System.EventHandler(this.cmdByYear_Click);
      // 
      // graphPanel
      // 
      this.graphPanel.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
      this.graphPanel.BackColor = System.Drawing.Color.White;
      this.graphPanel.Location = new System.Drawing.Point(123, 12);
      this.graphPanel.Name = "graphPanel";
      this.graphPanel.Size = new System.Drawing.Size(769, 454);
      this.graphPanel.TabIndex = 1;
      // 
      // txtFilename
      // 
      this.txtFilename.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
      this.txtFilename.BackColor = System.Drawing.SystemColors.Control;
      this.txtFilename.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
      this.txtFilename.Location = new System.Drawing.Point(123, 474);
      this.txtFilename.Name = "txtFilename";
      this.txtFilename.Size = new System.Drawing.Size(769, 26);
      this.txtFilename.TabIndex = 2;
      this.txtFilename.Text = "Crimes-2013-2015.csv";
      // 
      // button1
      // 
      this.button1.Location = new System.Drawing.Point(12, 114);
      this.button1.Name = "button1";
      this.button1.Size = new System.Drawing.Size(95, 62);
      this.button1.TabIndex = 3;
      this.button1.Text = "Arrest %";
      this.button1.UseVisualStyleBackColor = true;
      this.button1.Click += new System.EventHandler(this.cmdArrestPer_Click);
      // 
      // button2
      // 
      this.button2.Location = new System.Drawing.Point(12, 213);
      this.button2.Name = "button2";
      this.button2.Size = new System.Drawing.Size(95, 62);
      this.button2.TabIndex = 4;
      this.button2.Text = "By Crime";
      this.button2.UseVisualStyleBackColor = true;
      this.button2.Click += new System.EventHandler(this.button2_Click);
      // 
      // button3
      // 
      this.button3.Location = new System.Drawing.Point(12, 327);
      this.button3.Name = "button3";
      this.button3.Size = new System.Drawing.Size(95, 62);
      this.button3.TabIndex = 5;
      this.button3.Text = "By Area";
      this.button3.UseVisualStyleBackColor = true;
      this.button3.Click += new System.EventHandler(this.button3_Click);
      // 
      // textBox1
      // 
      this.textBox1.Location = new System.Drawing.Point(12, 283);
      this.textBox1.Name = "textBox1";
      this.textBox1.Size = new System.Drawing.Size(100, 29);
      this.textBox1.TabIndex = 6;
      this.textBox1.TextChanged += new System.EventHandler(this.textBox1_TextChanged);
      // 
      // textBox2
      // 
      this.textBox2.Location = new System.Drawing.Point(12, 395);
      this.textBox2.Name = "textBox2";
      this.textBox2.Size = new System.Drawing.Size(100, 29);
      this.textBox2.TabIndex = 7;
      this.textBox2.TextChanged += new System.EventHandler(this.textBox2_TextChanged);
      // 
      // button4
      // 
      this.button4.Location = new System.Drawing.Point(12, 434);
      this.button4.Name = "button4";
      this.button4.Size = new System.Drawing.Size(95, 62);
      this.button4.TabIndex = 8;
      this.button4.Text = "Chicago";
      this.button4.UseVisualStyleBackColor = true;
      this.button4.Click += new System.EventHandler(this.button4_Click);
      // 
      // Form1
      // 
      this.AutoScaleDimensions = new System.Drawing.SizeF(11F, 24F);
      this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
      this.ClientSize = new System.Drawing.Size(904, 508);
      this.Controls.Add(this.button4);
      this.Controls.Add(this.textBox2);
      this.Controls.Add(this.textBox1);
      this.Controls.Add(this.button3);
      this.Controls.Add(this.button2);
      this.Controls.Add(this.button1);
      this.Controls.Add(this.txtFilename);
      this.Controls.Add(this.graphPanel);
      this.Controls.Add(this.cmdByYear);
      this.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
      this.Margin = new System.Windows.Forms.Padding(6);
      this.Name = "Form1";
      this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
      this.Text = "Chicago Crime Analysis";
      this.Load += new System.EventHandler(this.Form1_Load);
      this.ResumeLayout(false);
      this.PerformLayout();

    }

    #endregion

    private System.Windows.Forms.Button cmdByYear;
    private System.Windows.Forms.Panel graphPanel;
    private System.Windows.Forms.TextBox txtFilename;
    private System.Windows.Forms.Button button1;
    private System.Windows.Forms.Button button2;
    private System.Windows.Forms.Button button3;
    private System.Windows.Forms.TextBox textBox1;
    private System.Windows.Forms.TextBox textBox2;
    private System.Windows.Forms.Button button4;
  }
}

